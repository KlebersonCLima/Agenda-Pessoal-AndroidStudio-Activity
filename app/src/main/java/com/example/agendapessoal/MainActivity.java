package com.example.agendapessoal;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.example.agendapessoal.database.CompromissosDB;
import com.example.agendapessoal.model.Compromisso;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        FragmentEntrada.OnCompromissoAdicionadoListener,
        FragmentVisualizacao.OnDataSelecionadaListener {

    private CompromissosDB compromissosDB;
    private FragmentVisualizacao fragmentVisualizacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar banco de dados
        compromissosDB = new CompromissosDB(this);
        compromissosDB.open();

        // Verificar se é a primeira execução e adicionar dados de exemplo
        List<Compromisso> compromissosExistentes = compromissosDB.buscarTodosCompromissos();
        if (compromissosExistentes.isEmpty()) {
            compromissosDB.adicionarDadosExemplo();
        }

        // Obter referência do fragmento de visualização
        fragmentVisualizacao = (FragmentVisualizacao)
                getSupportFragmentManager().findFragmentById(R.id.fragment_visualizacao);
    }

    @Override
    public void onCompromissoAdicionado(Compromisso compromisso) {
        // Salvar compromisso no banco de dados
        long id = compromissosDB.inserirCompromisso(compromisso);
        compromisso.setId(id);

        // Atualizar a visualização se necessário
        if (fragmentVisualizacao != null) {
            fragmentVisualizacao.atualizarCompromissos();
        }
    }

    @Override
    public void onDataSelecionada(Date data) {
        // Buscar compromissos da data no banco de dados
        List<Compromisso> compromissosDaData = compromissosDB.buscarCompromissosPorData(data);

        if (fragmentVisualizacao != null) {
            fragmentVisualizacao.mostrarCompromissosDaData(compromissosDaData, data);
        }
    }

    public CompromissosDB getCompromissosDB() {
        return compromissosDB;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Fechar conexão com o banco de dados
        if (compromissosDB != null) {
            compromissosDB.close();
        }
    }
}