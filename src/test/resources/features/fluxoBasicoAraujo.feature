Feature: fluxo basico site Araujo

  @Fluxo
  Scenario: Abrir o site, pesquisar o produto, selecionar o produto e excluir do carrinho
    Given Eu estou na pagina inicial da Araujo
    When Eu pesquiso por "Dipirona"
    And clico em comprar
    And adiciono o produto ao carrinho
    And Removo o produto do carrinho
    Then O produto deve ser removido do carrinho

  @Titulo
  Scenario: Abrir o site, pesquisar e tirar print
    Given Eu estou na pagina da Araujo
    When Eu pesquiso a palavra "Paracetamol"
    Then Eu registro o titulo da pagina em um arquivo