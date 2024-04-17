package Entity;

import java.io.Serializable;

public class Header implements Serializable {
    String origem;
    String destino;
    String acao;

    public Header(String origem, String destino, String acao) {
        this.origem = origem;
        this.destino = destino;
        this.acao = acao;
    }

    @Override
    public String toString() {
        return origem+"&"+destino+"&"+acao;
    }
}
