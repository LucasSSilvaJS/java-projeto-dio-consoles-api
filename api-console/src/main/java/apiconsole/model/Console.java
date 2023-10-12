package apiconsole.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "tb_console")
public class Console {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String img;
    @Column(unique = true)
    private String nome;
    @Column(precision = 4)
    private Number anoDeLancamento;
    private String geracao;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Jogo> jogos;

    //getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Number getAnoDeLancamento() {
        return anoDeLancamento;
    }

    public void setAnoDeLancamento(Number anoDeLancamento) {
        this.anoDeLancamento = anoDeLancamento;
    }

    public String getGeracao() {
        return geracao;
    }

    public void setGeracao(String geracao) {
        this.geracao = geracao;
    }

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }
}
