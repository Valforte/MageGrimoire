package com.gabrielvalforte.grimoire;

public class Magia {
    private int id;
    private int nivel;
    private String nome;
    private String pratica;
    private String acao;
    private String duracao;
    private String aspecto;
    private String custo;
    private String descri;
    private String nivelAd;
    private String classico;
    private String ordemCl;
    private String atributoCl;
    private String habilidadeCl;
    private String arcanoCl;
    private String descriCl;
    private int pag;
    private boolean fav;

    public Magia() {
    }

    @Override
    public String toString() {
        return "Magia [nome=" + nome + "]";
    }

    //region GETTERS and SETTERS

    public String getDetalhes() {
        return "Pág. " + this.getPag() + " - " +
                this.getAspecto() + " - "
                /*+ magia.getAcao() + " - " + magia.getDuracao() + " - "*/ +
                this.getCusto();
    }

    public String getNomeFull() {
        String n = "";
        for(int i = 0; i < this.getNivel(); i++)
            n += "●";
        n += " " + this.getNome();
        if(this.getNivelAd() != null && !this.getNivelAd().isEmpty())
            n += " (" + this.getNivelAd() + ") ";
        return n;
    }

    public String getNomeClassico() {
        String n = "";
        n += "Clássico";
        switch(this.getOrdemCl().substring(0,1)){
            case "S":
                n += " da ";
                break;
            case "G":
                n += " dos ";
                break;
            case "M":
                n += " do ";
                break;
            case "E":
                n += " da ";
                break;
            case "C":
                n += " do ";
                break;
            default:
                n += " do(a) ";
                break;
        }
        n += this.getOrdemCl();
        n += ": ";
        n += this.getClassico();
        n += ".";
        return n;
    }

    public String getParada() {
        String n = "";
        n += this.getAtributoCl();
        n += " + ";
        n += this.getHabilidadeCl();
        n += " + ";
        n += this.getArcanoCl();
        n += ".";
        return n;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPratica() {
        return pratica;
    }

    public void setPratica(String pratica) {
        this.pratica = pratica;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getAspecto() {
        return aspecto;
    }

    public void setAspecto(String aspecto) {
        this.aspecto = aspecto;
    }

    public String getCusto() {
        return custo;
    }

    public void setCusto(String custo) {
        this.custo = custo;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getClassico() {
        return classico;
    }

    public void setClassico(String classico) {
        this.classico = classico;
    }

    public String getOrdemCl() {
        return ordemCl;
    }

    public void setOrdemCl(String ordemCl) {
        this.ordemCl = ordemCl;
    }

    public String getAtributoCl() {
        return atributoCl;
    }

    public void setAtributoCl(String atributoCl) {
        this.atributoCl = atributoCl;
    }

    public String getHabilidadeCl() {
        return habilidadeCl;
    }

    public void setHabilidadeCl(String habilidadeCl) {
        this.habilidadeCl = habilidadeCl;
    }

    public String getArcanoCl() {
        return arcanoCl;
    }

    public void setArcanoCl(String arcanoCl) {
        this.arcanoCl = arcanoCl;
    }

    public String getDescriCl() {
        return descriCl;
    }

    public void setDescriCl(String descriCl) {
        this.descriCl = descriCl;
    }

    public String getNivelAd() {
        return nivelAd;
    }

    public void setNivelAd(String nivelAd) {
        this.nivelAd = nivelAd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPag() {
        return pag;
    }

    public void setPag(int pag) {
        this.pag = pag;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    //endregion

}