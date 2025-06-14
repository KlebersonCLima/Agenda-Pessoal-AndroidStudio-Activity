package com.example.agendapessoal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.agendapessoal.model.Compromisso;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentVisualizacao extends Fragment {

    private Button btnHoje, btnOutraData;
    private TextView tvCompromissos, tvDataVisualizacao;
    private OnDataSelecionadaListener listener;
    private Date dataAtual;

    public interface OnDataSelecionadaListener {
        void onDataSelecionada(Date data);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnDataSelecionadaListener) {
            listener = (OnDataSelecionadaListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visualizacao, container, false);

        inicializarComponentes(view);
        configurarListeners();

        // Mostrar compromissos de hoje por padrão
        dataAtual = new Date();
        mostrarCompromissosHoje();

        return view;
    }

    private void inicializarComponentes(View view) {
        btnHoje = view.findViewById(R.id.btn_hoje);
        btnOutraData = view.findViewById(R.id.btn_outra_data);
        tvCompromissos = view.findViewById(R.id.tv_compromissos);
        tvDataVisualizacao = view.findViewById(R.id.tv_data_visualizacao);
    }

    private void configurarListeners() {
        btnHoje.setOnClickListener(v -> mostrarCompromissosHoje());
        btnOutraData.setOnClickListener(v -> mostrarSeletorData());
    }

    private void mostrarCompromissosHoje() {
        dataAtual = new Date();
        if (listener != null) {
            listener.onDataSelecionada(dataAtual);
        }
    }

    private void mostrarSeletorData() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    Calendar dataSelecionadaCal = Calendar.getInstance();
                    dataSelecionadaCal.set(year, month, dayOfMonth);
                    dataAtual = dataSelecionadaCal.getTime();

                    if (listener != null) {
                        listener.onDataSelecionada(dataAtual);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    public void atualizarCompromissos() {
        // Buscar compromissos da data atual no banco de dados
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            List<Compromisso> compromissosDaData = mainActivity.getCompromissosDB().buscarCompromissosPorData(dataAtual);
            mostrarCompromissosDaData(compromissosDaData, dataAtual);
        }
    }

    public void mostrarCompromissosDaData(List<Compromisso> compromissos, Date data) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar hoje = Calendar.getInstance();
        Calendar dataCompromisso = Calendar.getInstance();
        dataCompromisso.setTime(data);

        String textoData;
        if (hoje.get(Calendar.YEAR) == dataCompromisso.get(Calendar.YEAR) &&
                hoje.get(Calendar.DAY_OF_YEAR) == dataCompromisso.get(Calendar.DAY_OF_YEAR)) {
            textoData = "Compromissos de hoje (" + sdf.format(data) + ")";
        } else {
            textoData = "Compromissos de " + sdf.format(data);
        }
        tvDataVisualizacao.setText(textoData);

        // ordena eles por horario
        Collections.sort(compromissos, new Comparator<Compromisso>() {
            @Override
            public int compare(Compromisso c1, Compromisso c2) {
                return c1.getHora().compareTo(c2.getHora());
            }
        });
        //exibi os compromissos
        if (compromissos.isEmpty()) {
            tvCompromissos.setText("Nenhum compromisso cadastrado para esta data.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < compromissos.size(); i++) {
                Compromisso compromisso = compromissos.get(i);
                sb.append(compromisso.toString());
                if (i < compromissos.size() - 1) {
                    sb.append("\n\n");
                }
            }
            tvCompromissos.setText(sb.toString());
        }
    }

    private List<Compromisso> filtrarCompromissosPorData(List<Compromisso> todosCompromissos, Date data) {
        List<Compromisso> compromissosFiltrados = new ArrayList<>();
        Calendar calData = Calendar.getInstance();
        calData.setTime(data);

        for (Compromisso compromisso : todosCompromissos) {
            Calendar calCompromisso = Calendar.getInstance();
            calCompromisso.setTime(compromisso.getData());

            if (calData.get(Calendar.YEAR) == calCompromisso.get(Calendar.YEAR) &&
                    calData.get(Calendar.DAY_OF_YEAR) == calCompromisso.get(Calendar.DAY_OF_YEAR)) {
                compromissosFiltrados.add(compromisso);
            }
        }

        return compromissosFiltrados;
    }

    @Override
    public void onResume() {
        super.onResume();
//so att
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            List<Compromisso> compromissosDaData = mainActivity.getCompromissosDB().buscarCompromissosPorData(dataAtual);
            mostrarCompromissosDaData(compromissosDaData, dataAtual);
        }
    }
}