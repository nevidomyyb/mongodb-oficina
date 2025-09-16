import dao.ClienteDAO;
import dao.ProdutoDAO;
import dao.VendaDAO;
import model.Cliente;
import model.Produto;
import model.Venda;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ClienteDAO clienteDAO = new ClienteDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();

        Cliente cliente1 = new Cliente(
                "João Silva",
                "123.456.789-00",
                "(11) 99999-1111",
                "joao@email.com"
        );

        Cliente cliente2 = new Cliente(
                "Maria Santos",
                "987.654.321-00",
                "(11) 88888-2222",
                "maria@email.com"
        );

        Cliente cliente3 = new Cliente(
                "Pedro Oliveira",
                "456.789.123-00",
                "(11) 77777-3333",
                "pedro@email.com"
        );

        clienteDAO.inserir(cliente1);
        clienteDAO.inserir(cliente2);
        clienteDAO.inserir(cliente3);

        Produto produto1 = new Produto(
                "OL001",
                "Óleo Motor 5W30",
                "Óleo sintético para motor",
                "Lubrificantes",
                new BigDecimal("45.90"),
                50,
                "Castrol"
        );

        Produto produto2 = new Produto(
                "FI002",
                "Filtro de Óleo",
                "Filtro de óleo para motor",
                "Filtros",
                new BigDecimal("25.50"),
                30,
                "Mann"
        );

        Produto produto3 = new Produto(
                "PA003",
                "Pastilha de Freio",
                "Pastilha de freio dianteira",
                "Freios",
                new BigDecimal("89.90"),
                20,
                "Bosch"
        );

        produtoDAO.inserir(produto1);
        produtoDAO.inserir(produto2);
        produtoDAO.inserir(produto3);

        Venda venda1 = new Venda(
                cliente1.getId(),
                Arrays.asList(produto1.getId(), produto2.getId()),
                Arrays.asList(2, 1),
                new BigDecimal("117.30"), // (45.90 * 2) + (25.50 * 1)
                "Cartão de Crédito",
                "Troca de óleo completa"
        );

        Venda venda2 = new Venda(
                cliente2.getId(),
                List.of(produto3.getId()),
                List.of(1),
                new BigDecimal("89.90"), // 89.90 * 1
                "Dinheiro",
                "Troca de pastilhas"
        );

        Venda venda3 = new Venda(
                cliente3.getId(),
                Arrays.asList(produto1.getId(), produto3.getId()),
                Arrays.asList(1, 2),
                new BigDecimal("225.70"), // (45.90 * 1) + (89.90 * 2)
                "PIX",
                "Manutenção preventiva"
        );

        VendaDAO vendaDAO = new VendaDAO();

        vendaDAO.inserir(venda1);
        vendaDAO.inserir(venda2);
        vendaDAO.inserir(venda3);

        List<Venda> vendas = vendaDAO.buscarPorCliente(cliente1.getId());
        System.out.println("Venda do cliente: " + cliente1.getNome());
        BigDecimal total = new BigDecimal("0");
        for (Venda venda : vendas) {
            System.out.println("Venda: " + venda.getId() + " | Quantidades: " + venda.getQuantidades());
            total = total.add(venda.getValorTotal());
        }
        System.out.println("Total: " + total);
    }
}
