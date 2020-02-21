package ipleiria.estg.deitour.workshops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class AddDevedorActivity extends AppCompatActivity {

    // Constante utilizada para marcar qual o nome do EXTRA passado na intent
    public static final String EXTRA_DEVEDOR = "devedor";

    //Variaveis da Vista:

    //Caixa de Texto nome
    private EditText editTextNome;

    //Caixa de Texto apelido
    private EditText editTextApelido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_devedor);

        inicializarVariaveis();
    }

    // Inicialização das variaveis de forma a fazer a ligação entre a classe e a vista
    // De forma a ganhar controlo sobre alguns componentes desta
    private void inicializarVariaveis () {

        this.editTextNome = findViewById(R.id.editTextNome);
        this.editTextApelido = findViewById(R.id.editTextApelido);
    }

    /*  Metodo onClickListener do botão "Cancelar"
     *  Chamado quando o utilizador quer cancelar a adição de uma nova pessoa a lista
     */
    public void onClickBtnCancelar(View view) {
        /*  A atividade foi inicializada de forma a que a atividade anterior
         *  estará a espera de um resultado. Para defenir esse resultado utilizamos
         *  o metodo setResult() onde o parametro passado é um inteiro que simboliza
         *  o resultado da atividade.
         */
        setResult(RESULT_CANCELED);
        //  O metodo finish() que indica que a atividade deverá ser terminada
        finish();
    }

    /*  Metodo onClickListener do botão "Adicionar"
     *  Chamado quando o utilizador está satisfeito com os nomes que introduziu
     *  e quer adicionar o novo devedor a lista de pessoas devedoras
     */
    public void onClickBtnAdicionar(View view) {

        //Em primeiro lugar são obtidos os valores presentes nos campos de texto
        String nome = this.editTextNome.getText().toString().trim();
        String apelido = this.editTextApelido.getText().toString().trim();

        // É feita a verificação a ver se algum deles está vazio
        if(!nome.isEmpty() && !apelido.isEmpty()) {

            // Criamos um novo devedor com os valores obtidos das caixas de texto
            Devedor devedor = new Devedor(nome, apelido);

            /*
             *  É criada uma intent, desta vez com o objetivo de enviar juntamente com esta
             *  o devedor (que implementa a interface Serializable, por isso é possivel ser adicionado
             *  a intent com o metodo putExtra()
             */
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DEVEDOR, devedor);


            /*  A atividade foi inicializada de forma a que a atividade anterior
             *  estará a espera de um resultado. Para defenir esse resultado utilizamos
             *  o metodo setResult() onde o parametro passado é um inteiro que simboliza
             *  o resultado da atividade. Neste caso, utilizamos o RESULT_OK porque correu
             *  tudo bem, e foi feito o pretendido
             */
            setResult(RESULT_OK, intent);
            //  O metodo finish() que indica que a atividade deverá ser terminada
            finish();
        }
        // Caso algum esteja vazio, despolta uma mensagem de erro
        else{
            Snackbar.make(findViewById(android.R.id.content), "Existem campos vazios. Verifique e tente novamente", Snackbar.LENGTH_LONG).show();
        }

    }
}
