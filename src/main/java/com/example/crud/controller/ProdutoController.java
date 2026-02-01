package com.exemplo.crud.controller;

import com.exemplo.crud.model.Produto;
import com.exemplo.crud.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody @Valid Produto produto) {
        return ResponseEntity.ok(repository.save(produto));
    }

    // READ - LISTAR
    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    // READ - POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid Produto novoProduto) {

        return repository.findById(id)
                .map(produto -> {
                    produto.setNome(novoProduto.getNome());
                    produto.setDescricao(novoProduto.getDescricao());
                    produto.setPreco(novoProduto.getPreco());
                    return ResponseEntity.ok(repository.save(produto));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

