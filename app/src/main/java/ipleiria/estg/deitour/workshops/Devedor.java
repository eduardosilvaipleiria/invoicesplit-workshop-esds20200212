package ipleiria.estg.deitour.workshops;

import java.io.Serializable;

public class Devedor implements Serializable {

    private String nome;
    private String apelido;
    private boolean pago;

    public Devedor(String nome, String apelido) {
        this.nome = nome;
        this.apelido = apelido;
        this.pago = false;
    }

    // O metodo toString() quando chamado deve devolver uma descrição em formato de String
    // sobre a instancia da classe em questão
    @Override
    public String toString() {
        if (pago) {
            return "Devedor -> " + nome + " " + apelido + " PAGO";
        } else {
            return "Devedor -> " + nome + " " + apelido;
        }

    }

    // Getter e Setter para o campo pago
    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public boolean getPago() {
        return this.pago;
    }
}
