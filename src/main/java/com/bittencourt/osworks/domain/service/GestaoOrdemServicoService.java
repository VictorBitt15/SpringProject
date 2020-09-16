package com.bittencourt.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bittencourt.osworks.api.model.Comentario;
import com.bittencourt.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.bittencourt.osworks.domain.exception.NegocioException;
import com.bittencourt.osworks.domain.model.Cliente;
import com.bittencourt.osworks.domain.model.OrdemServico;
import com.bittencourt.osworks.domain.model.StatusOrdemServico;
import com.bittencourt.osworks.domain.repository.ClienteRepository;
import com.bittencourt.osworks.domain.repository.ComentarioRepository;
import com.bittencourt.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(()-> new NegocioException("Cliente não encontrado"));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		ordemServico.finalizar();
		ordemServicoRepository.save(ordemServico);
	}

	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		return comentarioRepository.save(comentario);
	}
	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(()-> new EntidadeNaoEncontradaException("Ordem de servico nao encontrado"));
	}
	
}
