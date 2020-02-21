package ipleiria.estg.deitour.workshops;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    //Constante utilizada para simbolizar o objetivo da incialização da nova
    // atividade AddDevedorActivity
    private static final int REQUEST_ADD = 1;

    // Variaveis da vista:

    // Introdução da quantia da fatura
    private EditText editTextQuantia;
    // Campo de texto para mostrar o texto " Cada pessoa deve: x€ "
    private TextView txtQuantiaDividida;
    // Lista de Pessoas na Vista que participaram na fatura
    private ListView lstPessoas;
    // Botão Finalizar
    private Button btnFinalizar;
    // Botão Limpar
    private Button btnLimpar;
    // Botão Float Action Button
    private FloatingActionButton fab;

    // Variaveis da classe:

    // Adaptador da lista, responsavel por mostrar os devedores
    private ArrayAdapter<Devedor> adaptador;
    // Lista de Pessoas que participaram na fatura
    private ArrayList<Devedor> pessoas;
    // Contador de pessoas que ainda devem
    private int aDever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarVariaveis();

        // Inicialização da lista de devedores
        pessoas = new ArrayList<>();

        /*  Inicialização do adaptador que irá ser utilizado no componente ListView do layout activity_main
         *   O adaptador é o objeto que dita o que é apresentado e como é apresentado numa ListView
         *   O layout utilizado no adaptador (2º parametro) poderá ser layout costumizado ou,
         *   como é o caso da nossa aplicação, podemos utilizar um por predefinição que faz uso
         *   do metodo toString() dos objetos contidos na lista passada no 3º parâmetro.
         */
        adaptador = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                pessoas);

        // Definir o adaptador da lista como sendo o adaptador criado anteriormente
        lstPessoas.setAdapter(adaptador);
    }

    private void inicializarVariaveis() {

        // Nesta secção são inicializadas as variaveis que fazem a ligação entre a classe e a vista
        // de forma a ter controlo sobre os componentes desta

        this.editTextQuantia = findViewById(R.id.editTextQuantia);

        this.txtQuantiaDividida = findViewById(R.id.txtDivisaoQuantia);

        this.lstPessoas = findViewById(R.id.listView);

        this.fab = findViewById(R.id.floatingActionButton);

        this.btnFinalizar = findViewById(R.id.buttonFinalizar);

        this.btnLimpar = findViewById(R.id.buttonLimpar);

        // As propriedadades de visibilidade definidas de seguida podem também ser efetuadas através
        // da interface de design de vistas do Android Studio
        this.txtQuantiaDividida.setVisibility(View.GONE);

        this.btnLimpar.setVisibility(View.GONE);

    }


    @SuppressLint("RestrictedApi")
    public void onClickBtnFinalizar(View view) {

        //  Obter o texto introduzido pelo utilizador
        String texto = this.editTextQuantia.getText().toString().trim();

        /*  Operador ternario:
         *  se o texto estiver vazio -> atribui a variavel o valor 0
         *  caso contrario converte o texto num Inteiro
         */
        float quantia = texto.isEmpty() ? 0 : Float.parseFloat(texto);

        //  Obter a quantidade de pessoas que constam na lista para podermos realizar a divisão da quantia
        int quantidadePessoas = pessoas.size();

        //  Se a quantidade de pessoas for superior ou igual a 2 e também a quantia diferente de 0
        if (quantidadePessoas >= 2 && quantia != 0) {

            //  Realiza a divisão da quantia pelo numero de pessoas pelas quais é necessário dividir
            float quantiaDividida = (float) quantia / quantidadePessoas;

            //  Atualiza o texto do campo escondido com o valor resultante da divisão
            txtQuantiaDividida.setText("Cada pessoa deve: " + quantiaDividida + "€");

            //  O campo de texto escondigo passa a ser visivel
            txtQuantiaDividida.setVisibility(View.VISIBLE);

            // A partir deste momento, não poderemos fazer mais alterações à fatura, ou seja
            //   ocultamos os botões "FAB" e também "Finalizar", para que os utilizadores não
            // tenham acesso a essas funcionalidades neste momento.

            fab.setVisibility(View.GONE);
            btnFinalizar.setVisibility(View.GONE);

            // Adição do onItemClickListener a cada elemento da ListView lstPessoas para que seja possível
            //clicar num elemento da lista - implica que a classe implemente a interface AdapterView.OnItemClickListener
            lstPessoas.setOnItemClickListener(this);

            // Atribuição da quantidade de pessoas pela qual foi realizada a divisão à variavel aDever
            aDever = pessoas.size();
        }
        //  Alguma informação está em falta, ou existe algo de errado
        else {
            //  Mostra uma mensagem com uma pequena descrição daquilo que aconteceu / necessário realizar
            Snackbar.make(findViewById(android.R.id.content), "Algo não está certo. Por favor tente novamente", Snackbar.LENGTH_LONG).show();
        }

    }

    public void onClickBtnFab(View view) {
        // Cria a intenção de iniciar a atividade AddDevedorActivity
        Intent intent = new Intent(MainActivity.this, AddDevedorActivity.class);

        // Começa a nova atividade e fica à espera de um resultado desta
        startActivityForResult(intent, REQUEST_ADD);
    }

    //  Metodo responsável pelas funcionalidades desempenhadas aquando o selecionamento de um item da lista

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //Obtenção do devedor presente na posição selecionada
        Devedor devedor = (Devedor) lstPessoas.getItemAtPosition(i);

        //Toogle entre PAGO e NÃO PAGO (Não Representado)
        if (devedor.getPago()) {
            devedor.setPago(false);
            aDever++;
        } else {
            devedor.setPago(true);
            aDever--;
        }

        // Como realizamos alterações a lista temos de a atualizar
        adaptador.notifyDataSetChanged();

        // Se já não existirem devedores em divida então damos a opção de fazer a aplicação voltar ao estado inicial
        if (aDever == 0) {
            btnLimpar.setVisibility(View.VISIBLE);
        } else {
            btnLimpar.setVisibility(View.GONE);
        }


    }


    // O metodo onClickBtnLimpar faz o reset da atividade, voltando tudo ao seu estado inicial
    @SuppressLint("RestrictedApi")
    public void onClickBtnLimpar(View view) {

        // Esvaziar a lista de pessoas
        pessoas.clear();
        // Atualizar o component ListView pois a lista que contem os dados presentiados na vista foi alterada
        adaptador.notifyDataSetChanged();
        // Voltar a repor o campo de introdução da quantia no seu estado inicial
        editTextQuantia.setText("");
        // Voltar a mostrar o botão "Finalizar"
        btnFinalizar.setVisibility(View.VISIBLE);
        // Voltar a mostrar o botão "FAB"
        fab.setVisibility(View.VISIBLE);
        // Voltar a repor o texto "Cada pessoa deve: x€"  ao estado inicial
        txtQuantiaDividida.setText("");
        // Voltar a esconder o texto "Cada pessoa deve: x€"
        txtQuantiaDividida.setVisibility(View.GONE);
        // Voltar a esconder o botão "Limpar"
        btnLimpar.setVisibility(View.GONE);
     }

    /*
     *  O metodo onActivityResult é chamado quando a atividade que iniciamos com startActivityForResult() termina.
     *  Como o request code poderá variar, é boa pratica ter condições de verificação de qual foi o objetivo (requestCode)
     *  e de que maneira terminou a atividade (resultCode), de forma a defenir o comportamento especifico de acordo com
     *  a combinação obtida.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verificação da combinação requestCode e resultCode para poder adequar as funcionalidades
        // a combinação request e result
        if (requestCode == REQUEST_ADD && resultCode == RESULT_OK) {
            // Extrair o extra inserido na intenção que retornou
            // Converter para Devedor (lembrar a definição de Serializable)
            Devedor devedor = (Devedor) data.getSerializableExtra(AddDevedorActivity.EXTRA_DEVEDOR);
            // Adicionar o novo devedor a lista de pessoas a dever já existente
            pessoas.add(devedor);
            // Atualizar a lista da vista pois foi adicionado a lista pessoas um novo devedor
            adaptador.notifyDataSetChanged();

        } else {
            //Se cair aqui, é porque o utilizador escolheu cancelar, ou então voltou com outro resultCode
        }
    }
}
