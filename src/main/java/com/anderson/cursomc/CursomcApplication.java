package com.anderson.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anderson.cursomc.domain.Categoria;
import com.anderson.cursomc.domain.Cidade;
import com.anderson.cursomc.domain.Cliente;
import com.anderson.cursomc.domain.Endereco;
import com.anderson.cursomc.domain.Estado;
import com.anderson.cursomc.domain.Pagamento;
import com.anderson.cursomc.domain.PagamentoComBoleto;
import com.anderson.cursomc.domain.PagamentoComCartao;
import com.anderson.cursomc.domain.Pedido;
import com.anderson.cursomc.domain.Produto;
import com.anderson.cursomc.domain.enums.EstadoPagamento;
import com.anderson.cursomc.domain.enums.TipoCliente;
import com.anderson.cursomc.repositories.CategoriaRepository;
import com.anderson.cursomc.repositories.CidadeRepository;
import com.anderson.cursomc.repositories.ClienteRepository;
import com.anderson.cursomc.repositories.EnderecoRepository;
import com.anderson.cursomc.repositories.EstadoRepository;
import com.anderson.cursomc.repositories.PagamentoRepository;
import com.anderson.cursomc.repositories.PedidoRepository;
import com.anderson.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto prod1 = new Produto(null, "Computador", 2000.00);
		Produto prod2 = new Produto(null, "Impressora", 2000.00);
		Produto prod3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2));
		
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));
		
		Estado estado1 = new Estado(null, "Minas Gerais");
		Estado estado2 = new Estado(null, "São Paulo");
		
		Cidade cidade1 = new Cidade(null, "Uberlância", estado1);
		Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
		Cidade cidade3 = new Cidade(null, "Campinas", estado2);
		
		estado1.getCidades().addAll(Arrays.asList(cidade1));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(prod1, prod2, prod3));
		
		estadoRepository.saveAll(Arrays.asList(estado1, estado2));
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));
		
		Cliente cliente1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cliente1.getTelefones().addAll(Arrays.asList("27767809", "65768798"));
		
		Endereco endereco1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834",cliente1, cidade1 );
		Endereco endereco2 = new Endereco(null, "Rua Flores", "105", "Sala 800", "Centro", "09878688",cliente1, cidade2 );
	
		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
		
		clienteRepository.saveAll(Arrays.asList(cliente1));
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido pedido1 = new Pedido(null, dateFormat.parse("14/09/2019 13:13"), cliente1, endereco1);
		Pedido pedido2 = new Pedido(null, dateFormat.parse("10/09/2019 15:15"), cliente1, endereco2);
		
		Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 7);
		pedido1.setPagamento(pagamento1);
		
		Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, dateFormat.parse("20/10/2019 00:00"), null);
		pedido2.setPagamento(pagamento2);
		
		cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));
		
	}
	
	

}
