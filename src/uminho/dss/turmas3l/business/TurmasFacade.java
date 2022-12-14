/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas práticas de DSS, representando
 *  uma solução em construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada.
 *  É disponibilizado para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o
 *  código fornecido e a procurar soluções alternativas, à medida que forem adquirindo mais conhecimentos.
 */
package uminho.dss.turmas3l.business;

import uminho.dss.turmas3l.data.TurmaDAO;

import java.util.*;

/**
 * Versão muito simplificada de um Facade para a turma.
 *
 * @author JFC
 * @version 20201206
 */
public class TurmasFacade implements ITurmasFacade {

    private Map<String,Turma> turmas;
    private Map<String,Aluno> alunos;

    public TurmasFacade() {
        this.turmas = TurmaDAO.getInstance();
        this.alunos = new HashMap<>();
    }

    /**
     * Método que devolve todos os alunos registados.
     *
     * @return todos os alunos registados
     */
    @Override
    public Collection<Aluno> getAlunos() {
        return new ArrayList<>(this.alunos.values());
    }

    /**
     * Método que remove um aluno da turma.
     *
     * @param tid id da turma
     * @param num número do aluno a remover
     */
    @Override
    public void removeAlunoTurma(String tid, String num) {
        Turma t = this.turmas.get(tid);
        t.removeAluno(num);
        this.turmas.put(tid, t);  // Necessário fazer put para actualizar a BD.
    }

    /**
     * Método que devolve os alunos de uma turma.
     *
     * Transforma a lista de Números de aluno que vem do TurmaDAO em alunos que vai buscar ao AlunoDAO.
     *
     * @param tid id da turma
     * @return alunos da turma
     */
    @Override
    public Collection<String> getAlunos(String tid) {

        return turmas.get(tid).getAlunos();
    }

    /**
     * @return todas as turmas
     */
    @Override
    public Collection<Turma> getTurmas() {
        return turmas.values();
    }

    /**
     * @param a aluno a adicionar
     */
    @Override
    public void adicionaAluno(Aluno a) {
        this.alunos.put(a.getNumero(), a);
    }

    /**
     * @param t turma a dicionar
     */
    @Override
    public void adicionaTurma(Turma t) {
        this.turmas.put(t.getId(), t);
    }

    /**
     * @param tid id da turma que vai mudar de sala
     * @param s nova sala da turma
     */
    @Override
    public void alteraSalaDeTurma(String tid, Sala s) {
        Turma t = turmas.get(tid);
        t.setSala(s);
        turmas.put(t.getId(), t);    // Para actualizar a BD!
    }

    /**
     * @param tid id da turma a procurar
     * @return true se a turma existe
     */
    @Override
    public boolean existeTurma(String tid) {
        return turmas.containsKey(tid);
    }

    /**
     * @param num número do aluno a procurar
     * @return true se o aluno existe
     */
    @Override
    public Aluno procuraAluno(String num) {
        return this.alunos.get(num);
    }

    /**
     * @param num número do aluno a procurar
     * @return true se o aluno existe
     */
    @Override
    public boolean existeAluno(String num) {
        return this.alunos.containsKey(num);
    }

    /**
     * Método que adiciona um aluno à turma.
     *
     * NOTA 1: Falta garantir que aluno existe
     * NOTA 2: Uma vez que o modelo relacional só permite que o aluno esteja numa turma, este será retirado da
     * turma anterior, se estiver numa.
     *
     * @param tid id da turma onde se vai colocar o aluno
     * @param num número do aluno
     */
    @Override
    public void adicionaAlunoTurma(String tid, String num) {
        Turma t = this.turmas.get(tid);
        t.adiciona(num);
        turmas.put(tid, t);
    }

    /**
     * @param tid Turma a pesqueisar
     * @param num Número de aluno a pesquisar
     * @return True se aluno existe na turma
     */
    @Override
    public boolean existeAlunoEmTurma(String tid, String num) {
        return this.turmas.get(tid).existeAluno(num);
    }

}
