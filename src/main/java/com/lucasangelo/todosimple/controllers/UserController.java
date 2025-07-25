package com.lucasangelo.todosimple.controllers;

import com.lucasangelo.todosimple.models.User;
import com.lucasangelo.todosimple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController // Define que esta classe é um controller REST (retorna dados diretamente no corpo da resposta)
@RequestMapping("/user") // Define o caminho base da URL para todos os métodos desse controller
@Validated // Permite o uso de validações com grupos personalizados (como User.CreateUser e User.UpdateUser)
public class UserController {

    @Autowired // Injeta automaticamente a instância de UserService (injeção de dependência)
    private UserService userService;

    @GetMapping("/{id}") // Mapeia requisições GET com um valor dinâmico no caminho (ex: /user/1)
    public ResponseEntity<User> findById(@PathVariable Long id) { // @PathVariable indica que o valor de 'id' vem da URL
        User obj = this.userService.findById(id); // Chama o service para buscar o usuário
        return ResponseEntity.ok().body(obj); // Retorna resposta 200 OK com o corpo contendo o usuário
    }

    @PostMapping // Mapeia requisições POST para /user (usado para criar um novo recurso)
    @Validated(User.CreatUser.class) // Aplica regras de validação do grupo CreateUser
    public ResponseEntity<Void> create(@Valid @RequestBody User obj) {
        // @Valid ativa as validações (como @NotBlank) no objeto User
        // @RequestBody diz ao Spring para pegar o JSON da requisição e converter para objeto User
        this.userService.create(obj); // Chama o service para criar o usuário
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest() // Cria URI do novo recurso
                .path("/{id}").buildAndExpand(obj.getId()).toUri(); // Substitui {id} pelo ID do novo usuário
        return ResponseEntity.created(uri).build(); // Retorna 201 Created com header "Location" apontando para o novo recurso
    }

    @PutMapping("/{id}") // Mapeia requisições PUT para /user/{id} (usado para atualizar um recurso)
    @Validated(User.UpdateUser.class) // Aplica regras de validação do grupo UpdateUser
    public ResponseEntity<Void> update(@Valid @RequestBody User obj, @PathVariable Long id) {
        // @RequestBody: JSON → objeto User | @PathVariable: pega 'id' da URL
        obj.setId(id); // Garante que o ID do objeto a ser atualizado é o mesmo da URL
        this.userService.update(obj); // Chama o service para atualizar
        return ResponseEntity.noContent().build(); // Retorna 204 No Content (atualização bem-sucedida, sem corpo)
    }

    @DeleteMapping("/{id}") // Mapeia requisições DELETE para /user/{id}
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.userService.delete(id); // Chama o service para deletar o usuário
        return ResponseEntity.noContent().build(); // Retorna 204 No Content (deleção bem-sucedida)
    }
}

