set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from restaurante_usuario_responsavel;
delete from usuario;
delete from usuario_grupo;
delete from pedido;
delete from item_pedido;
delete from foto_produto;

set foreign_key_checks = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;
alter table pedido auto_increment = 1;
alter table item_pedido auto_increment = 1;

insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Argentina');
insert into cozinha (id, nome) values (4, 'Brasileira');

insert into estado (id, nome) values (1, 'Minas Gerais');
insert into estado (id, nome) values (2, 'São Paulo');
insert into estado (id, nome) values (3, 'Ceará');

insert into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert into cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert into cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);

insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values (1, 'Gourmet', 10, 1, utc_timestamp, utc_timestamp, true, true, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (2, 'Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (3, 'Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (4, 'Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (5, 'Lanchonete do Seu Zé', 11, 4, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (6, 'Bar da Margarida', 6, 4, utc_timestamp, utc_timestamp, true, true);

insert into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porco assado', 'Carne suína com limão', 78.90, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Camarão', 'Porção de camarões', 110, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada tailandesa', 'Salada de gengibre, limão, pimenta chilli vermelha, óleo de gergelim, azeite, shoyu, pepino e coentro', 87.20, 1, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Samosa', 'Pastéis fritos que podem ser recheados com lentilhas', 21, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bife Ancho', 'Bife que tem cerca de dois dedos de altura, é assado na grelha e depois na churrasqueira', 79, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Choripán', 'Pão com linguiça', 89, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Espetinho', 'Acompanha farinha', 8, 1, 6);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('X-Bacon', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);

insert into grupo (id, nome) values (1, 'Gerente'), (2, 'Vendedor'), (3, 'Secretária'), (4, 'Cadastrador');

insert into usuario (id, nome, email, senha, data_cadastro) values
(1, 'João da Silva', 'joao@springfood.com', '123', utc_timestamp),
(2, 'Maria Alves', 'maria@springfood.com', '123', utc_timestamp),
(3, 'José Marcos', 'jose@springfood.com', '123', utc_timestamp),
(4, 'Luana Marques', 'luana@springfood.com', '123', utc_timestamp),
(5, 'Marcos Garcia', 'marcos@springfood.com', '123', utc_timestamp);

insert into grupo_permissao (grupo_id, permissao_id) values (1, 1), (1, 2), (2, 1), (2, 2), (3, 1);

insert into usuario_grupo (usuario_id, grupo_id) values (1, 1), (1, 2), (2, 2);

insert into restaurante_usuario_responsavel (restaurante_id, usuario_id) values (1, 5), (3, 5);

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, subtotal, taxa_frete, valor_total)
values (1, '47387b24-a26d-46e6-b56e-92a098cbd66f', 1, 1, 1, 1, '15800-000', 'Rua Campinas', '100', '', 'Brasil', 'CRIADO', utc_timestamp, 298.90, 10, 308.90);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (1, 1, 1, 1, 78.9, 78.9, null);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (2, 1, 2, 2, 110, 220, 'Sem sal por favor');

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, subtotal, taxa_frete, valor_total)
values (2, '5332cd76-9716-4f60-b1dd-074832705ff6', 4, 1, 2, 1, '15804-100', 'Rua Maranhão', '50', 'apto. 100', 'Centro', 'CRIADO', utc_timestamp, 79, 0, 79);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (3, 2, 6, 1, 79, 79, 'Ao ponto');

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
values (3, '15b82c4c-4a8b-11ec-81d3-0242ac130003', 1, 1, 1, 1, '15802-222', 'Rua Pernambuco', '10', null, 'Brasil', 'ENTREGUE', '2021-09-25 09:00:00', '2021-09-25 09:02:05', '2021-09-27 10:53:40', 110, 10, 120);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (4, 3, 2, 1, 110, 110, null);

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, subtotal, taxa_frete, valor_total)
values (4, '1ecf7e2a-4a8b-11ec-81d3-0242ac130003', 1, 2, 1, 1, '15741-800', 'Rua Santos', '50', 'Apto 100', 'Centro', 'CRIADO', utc_timestamp, 181, 5, 186);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (5, 4, 3, 2, 90.5, 181, null);

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, data_confirmacao, data_entrega, subtotal, taxa_frete, valor_total)
values (5, '331ed7fe-4a8b-11ec-81d3-0242ac130003', 1, 3, 2, 1, '15800-000', 'Rua XV de Novembro', '90', 'Casa', 'Martins', 'ENTREGUE', '2021-11-20 21:00:00', '2021-11-20 21:01:30', '2021-11-25 12:00:42', 90.5, 10, 100.5);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (6, 5, 3, 1, 90.5, 90.5, null);
