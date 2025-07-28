package com.lucasangelo.todosimple.services;

import com.lucasangelo.todosimple.models.User;
import com.lucasangelo.todosimple.repositories.UserRepositoy;
import com.lucasangelo.todosimple.services.exceptions.DataBindingViolationException;
import com.lucasangelo.todosimple.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service // Indica que essa classe é um "Service" do Spring, ou seja, uma classe de serviço que contém regras de negócio
public class UserService {

    @Autowired // Injeta automaticamente a dependência do repositório de usuários
    private UserRepositoy userRepositoy;

    // Método para buscar um usuário pelo seu ID
    public User findById(Long id) {
        Optional<User> user = this.userRepositoy.findById(id);
        // Se o usuário estiver presente, retorna; se não, lança uma exceção personalizada
        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
    }

    @Transactional // Garante que todas as operações dentro do método sejam executadas em uma única transação
    public User create(User obj) {
        // Garante que o ID esteja nulo para o JPA entender que é um novo objeto
        obj.setId(null);
        // Salva o novo usuário no banco de dados
        obj = this.userRepositoy.save(obj);
        // Retorna o usuário salvo com ID e tarefas persistidas
        return obj;
    }

    @Transactional // Também utiliza transação, já que está alterando dados no banco
    public User update(User obj) {
        // Busca o usuário existente pelo ID para garantir que ele existe
        User newObj = findById(obj.getId());
        // Atualiza apenas a senha do usuário. Você pode adicionar mais campos se quiser permitir atualizações completas.
        newObj.setPassword(obj.getPassword());
        // Salva e retorna o usuário atualizado
        return this.userRepositoy.save(newObj);
    }

    // Método para deletar um usuário pelo ID
    public void delete(Long id) {
        findById(id);
        try {
            this.userRepositoy.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }
}
