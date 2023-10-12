package apiconsole.controller;

import apiconsole.exceptions.ConsoleNaoEncontradoException;
import apiconsole.model.Console;
import apiconsole.model.Jogo;
import apiconsole.service.ConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consoles")
public class ConsoleController {
    private final ConsoleService consoleService;
    @Autowired
    public ConsoleController(ConsoleService consoleService){
        this.consoleService = consoleService;
    }

    @GetMapping
    public List<Console> listarConsoles(){
        return consoleService.listarTodosConsoles();
    }

    @GetMapping("/{id}")
    public Console encontrarConsolePorId(@PathVariable Long id){
        return consoleService.encontrarConsolePorId(id).orElse(null);
    }

    @PostMapping
    public Console adicionarConsole(@RequestBody Console console){
        return consoleService.salvarConsole(console);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarConsole(@PathVariable Long id, @RequestBody Console consoleAtualizado){
        try{
            Console console = consoleService.atualizarConsole(id, consoleAtualizado);
            return new ResponseEntity<>(console, HttpStatus.OK);
        }catch (ConsoleNaoEncontradoException ex){
            return new ResponseEntity<>("Console não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarConsole(@PathVariable Long id){
        try{
            boolean deletado = consoleService.deletarConsole(id);
            if(deletado){
                return new ResponseEntity<>("Console deletado com sucesso", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Console não encontrado", HttpStatus.NOT_FOUND);
            }
        }catch (ConsoleNaoEncontradoException ex){
            return new ResponseEntity<>("Console não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{consoleId}/jogos")
    public ResponseEntity<Console> adicionarJogoAoConsole(@PathVariable Long consoleId, @RequestBody Jogo jogo){
        Console console = consoleService.adicionarJogoAoConsole(consoleId, jogo);
        return new ResponseEntity<>(console, HttpStatus.CREATED);
    }

    @DeleteMapping("/{consoleId}/jogos/{jogoId}")
    public ResponseEntity<Console> removerJogoDoConsole(@PathVariable Long consoleId, @PathVariable Long jogoId){
        Console console = consoleService.removerJogoDoConsole(consoleId, jogoId);
        return new ResponseEntity<>(console, HttpStatus.OK);
    }

    @PutMapping("/{consoleId}/jogos/{jogoId}")
    public ResponseEntity<Console> atualizarJogoNoConsole(@PathVariable Long consoleId, @PathVariable Long jogoId, @RequestBody Jogo jogoAtualizado){
        Console console = consoleService.atualizarJogoNoConsole(consoleId, jogoId, jogoAtualizado);
        return new ResponseEntity<>(console, HttpStatus.OK);
    }

    @GetMapping("/{consoleId}/jogos")
    public ResponseEntity<Jogo> buscarJogoNoConsolePorNome(
            @PathVariable Long consoleId,
            @RequestParam("nome") String nome){
        Jogo jogo = consoleService.buscarJogoNoConsolePorNome(consoleId, nome);
        if(jogo != null){
            return new ResponseEntity<>(jogo, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
