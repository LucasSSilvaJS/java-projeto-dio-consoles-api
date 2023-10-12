package apiconsole.service;

import apiconsole.exceptions.ConsoleNaoEncontradoException;
import apiconsole.model.Console;
import apiconsole.model.Jogo;
import apiconsole.repository.ConsoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsoleService {
    private final ConsoleRepository consoleRepository;

    @Autowired
    public ConsoleService(ConsoleRepository consoleRepository){
        this.consoleRepository = consoleRepository;
    }

    public List<Console> listarTodosConsoles(){
        return consoleRepository.findAll();
    }

    public Optional<Console> encontrarConsolePorId(Long id){
        if(id == null){
            throw new IllegalArgumentException("O id não pode ser nulo");
        }
        return consoleRepository.findById(id);
    }

    public Console salvarConsole(Console console){
        return consoleRepository.save(console);
    }

    public Console atualizarConsole(Long id, Console consoleAtualizado){
        if(!consoleRepository.existsById(id)){
            throw new ConsoleNaoEncontradoException("Console com id " + id + " não encontrado");
        }
        consoleAtualizado.setId(id);
        return consoleRepository.save(consoleAtualizado);
    }

    public boolean deletarConsole(Long id){
        if(!consoleRepository.existsById(id)) {
            throw new ConsoleNaoEncontradoException("Console com id " + id + " não encontrado");
        }
        consoleRepository.deleteById(id);
        return true;
    }

    public Console adicionarJogoAoConsole(Long consoleId, Jogo jogo){
        Console console = consoleRepository.findById(consoleId)
                .orElseThrow(() -> new ConsoleNaoEncontradoException("Console com o ID " + consoleId + "não encontrado"));
        List<Jogo> jogos = console.getJogos();
        jogos.add(jogo);
        return consoleRepository.save(console);
    }

    public Console removerJogoDoConsole(Long consoleId, Long jogoId){
        Console console = consoleRepository.findById(consoleId)
                .orElseThrow(() -> new ConsoleNaoEncontradoException("Console com o ID " + consoleId + "não encontrado"));
        List<Jogo> jogos = console.getJogos();
        jogos.removeIf(jogo -> jogo.getId().equals(jogoId));
        console.setJogos(jogos);
        return consoleRepository.save(console);
    }

    public Console atualizarJogoNoConsole(Long consoleId, Long jogoId, Jogo jogoAtualizado){
        Console console = consoleRepository.findById(consoleId)
                .orElseThrow(() -> new ConsoleNaoEncontradoException("Console com o ID " + consoleId + "não encontrado"));
        List<Jogo> jogos = console.getJogos();
        for(int i = 0; i < jogos.size(); i++){
            Jogo jogo = jogos.get(i);
            if(jogo.getId().equals(jogoId)){
                jogos.set(i,jogoAtualizado);
                break;
            }
        }
        console.setJogos(jogos);
        return consoleRepository.save(console);
    }

    public Jogo buscarJogoNoConsolePorNome(Long consoleId, String nome){
        Console console = consoleRepository.findById(consoleId)
                .orElseThrow(() -> new ConsoleNaoEncontradoException("Console com o ID " + consoleId + "não encontrado"));
        List<Jogo> jogos = console.getJogos();
        for(Jogo jogo: jogos){
            if(jogo.getNome().equalsIgnoreCase(nome)){
                return jogo;
            }
        }
        return null;
    }
}
