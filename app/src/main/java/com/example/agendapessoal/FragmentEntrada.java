package com.example.agendapessoal;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.agendapessoal.model.Compromisso;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragmentEntrada extends Fragment {

    private Button btnData, btnHora, btnOk;
    private EditText etDescricao;
    private TextView tvDataHoraSelecionada;

    private Date dataSelecionada;
    private String horaSelecionada;
    private OnCompromissoAdicionadoListener listener;

    public interface OnCompromissoAdicionadoListener {
        void onCompromissoAdicionado(Compromisso compromisso);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCompromissoAdicionadoListener) {
            listener = (OnCompromissoAdicionadoListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entrada, container, false);

        inicializarComponentes(view);
        configurarListeners();

        return view;
    }

    private void inicializarComponentes(View view) {
        btnData = view.findViewById(R.id.btn_data);
        btnHora = view.findViewById(R.id.btn_hora);
        btnOk = view.findViewById(R.id.btn_ok);
        etDescricao = view.findViewById(R.id.et_descricao);
        tvDataHoraSelecionada = view.findViewById(R.id.tv_data_hora_selecionada);
    }

    private void configurarListeners() {
        btnData.setOnClickListener(v -> mostrarSeletorData());
        btnHora.setOnClickListener(v -> mostrarSeletorHora());
        btnOk.setOnClickListener(v -> adicionarCompromisso());
    }

    private void mostrarSeletorData() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    Calendar dataSelecionadaCal = Calendar.getInstance();
                    dataSelecionadaCal.set(year, month, dayOfMonth);
                    dataSelecionada = dataSelecionadaCal.getTime();
                    atualizarTextoDataHora();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void mostrarSeletorHora() {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                (view, hourOfDay, minute) -> {
                    horaSelecionada = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                    atualizarTextoDataHora();
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );

        timePickerDialog.show();
    }

    private void atualizarTextoDataHora() {
        StringBuilder texto = new StringBuilder();

        if (dataSelecionada != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            texto.append("Data: ").append(sdf.format(dataSelecionada));
        }

        if (horaSelecionada != null) {
            if (texto.length() > 0) {
                texto.append(" - ");
            }
            texto.append("Hora: ").append(horaSelecionada);
        }

        if (texto.length() == 0) {
            texto.append("Data e hora não selecionadas");
        }

        tvDataHoraSelecionada.setText(texto.toString());
    }

    private void adicionarCompromisso() {
        String descricao = etDescricao.getText().toString().trim();

        if (dataSelecionada == null) {
            Toast.makeText(requireContext(), "Selecione uma data", Toast.LENGTH_SHORT).show();
            return;
        }

        if (horaSelecionada == null) {
            Toast.makeText(requireContext(), "Selecione uma hora", Toast.LENGTH_SHORT).show();
            return;
        }

        if (descricao.isEmpty()) {
            Toast.makeText(requireContext(), "Digite uma descrição", Toast.LENGTH_SHORT).show();
            return;
        }

        Compromisso novoCompromisso = new Compromisso(dataSelecionada, horaSelecionada, descricao);

        if (listener != null) {
            listener.onCompromissoAdicionado(novoCompromisso);
        }

        limparCampos();

        Toast.makeText(requireContext(), "Compromisso adicionado com sucesso!", Toast.LENGTH_SHORT).show();
    }

    private void limparCampos() {
        dataSelecionada = null;
        horaSelecionada = null;
        etDescricao.setText("");
        tvDataHoraSelecionada.setText("Data e hora não selecionadas");
    }
}