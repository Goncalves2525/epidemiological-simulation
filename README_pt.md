# Simulação Epidemiológica com Métodos Numéricos

Este projeto implementa um simulador epidemiológico que utiliza equações diferenciais para modelar a propagação de doenças infeciosas. O programa oferece dois métodos de integração numérica - Método de Euler e Método de Runge-Kutta de 4.ª ordem (RK4) - para resolver as equações diferenciais que modelam a dinâmica da população.

## Funcionalidades

- Leitura de parâmetros epidemiológicos de ficheiros CSV
- Implementação do modelo SEIR (Suscetível-Exposto-Infecioso-Recuperado)
- Dois métodos de integração numérica:
    - Método de Euler
    - Método de Runge-Kutta de 4.ª ordem (RK4)
- Geração automática de gráficos usando Gnuplot
- Interface interativa para definição de parâmetros
- Suporte à execução via linha de comandos com argumentos

## Requisitos do Sistema

- Java Runtime Environment (JRE) 8 ou superior
- Gnuplot (para geração de gráficos)

## Instalação

1. Clone o repositório:
   ```
   git clone https://github.com/seu-utilizador/nome-do-repositorio.git
   ```

2. Instale o Gnuplot:
    - **macOS** (usando Homebrew):
      ```
      brew install gnuplot
      ```
    - **Windows**: Descarregue o instalador em [Gnuplot Download](http://www.gnuplot.info/download.html)
    - **Linux**:
      ```
      sudo apt-get install gnuplot    # Ubuntu/Debian
      sudo dnf install gnuplot        # Fedora
      ```

## Como Utilizar

### Modo Interativo

1. Execute o programa sem argumentos:
   ```
   java Main
   ```

2. Siga as instruções no ecrã para:
    - Selecionar o método de integração (Euler ou RK4)
    - Fornecer o nome do ficheiro CSV de entrada
    - Definir o passo de integração (valor entre 0 e 1)
    - Especificar o tamanho da população
    - Definir o número de dias para análise
    - Escolher qual(is) conjunto(s) de parâmetros analisar

### Modo Linha de Comandos

Execute o programa com os argumentos na seguinte ordem:
```
java Main ficheiro_entrada.csv -m [1|2] -p [0-1] -t [população] -d [dias] ficheiro_saida.csv
```

Onde:
- `ficheiro_entrada.csv`: Ficheiro CSV contendo os parâmetros epidemiológicos
- `-m`: Método (1 para Euler, 2 para RK4)
- `-p`: Passo de integração (valor entre 0 e 1)
- `-t`: Tamanho da população (número inteiro positivo)
- `-d`: Número de dias para análise (número inteiro positivo)
- `ficheiro_saida.csv`: Nome do ficheiro de saída

## Formato do Ficheiro de Entrada

O ficheiro CSV de entrada deve ter o seguinte formato:
```
Nome,beta,gama,ro,alfa
Pessoa1,0.5,0.1,0.15,0.2
Pessoa2,0.7,0.2,0.1,0.3
```

Onde:
- `Nome`: Identificador do conjunto de parâmetros
- `beta`: Taxa de transmissão
- `gama`: Taxa de recuperação
- `ro`: Taxa de exposição
- `alfa`: Taxa de incubação

## Saídas

O programa gera dois tipos de ficheiros:
1. **Ficheiros CSV** com os resultados da simulação
2. **Ficheiros de script Gnuplot (.gp)** para visualização gráfica

### Visualização dos Gráficos

Para visualizar manualmente os gráficos gerados:

1. Abra o terminal e navegue até à diretoria com os ficheiros .gp
2. Execute o comando:
   ```
   gnuplot
   ```
3. No prompt do Gnuplot, carregue o script:
   ```
   load 'nome_do_ficheiro.gp'
   ```

## Estrutura do Projeto

- `Main.java`: Classe principal que gere a interface do utilizador e fluxo do programa
- `Calculos.java`: Implementa os métodos de integração numérica (Euler e RK4)
- `OperacaoFicheiros.java`: Gere a leitura e escrita de ficheiros
- `Gnuplot.java`: Cria scripts para o Gnuplot e gera gráficos
- `Validacoes.java`: Verifica a validade dos inputs do utilizador

## Modelo Matemático

O projeto implementa o modelo SEIR com as seguintes equações diferenciais:

- dS/dt = -β × S × I / N
- dE/dt = β × S × I / N - α × E
- dI/dt = α × E - γ × I
- dR/dt = γ × I

Onde:
- S: População suscetível
- E: População exposta (infetada mas não infeciosa)
- I: População infeciosa
- R: População recuperada
- N: População total
- β: Taxa de transmissão
- α: Taxa de incubação (transição de exposto para infecioso)
- γ: Taxa de recuperação

## Licença

Este projeto está licenciado sob [inserir a sua licença aqui, por exemplo, MIT License].

## Autores

[O seu nome e contacto/GitHub]

## Agradecimentos

- [Quaisquer agradecimentos ou referências que queira incluir]