# Simulação Epidemiológica com Métodos Numéricos

Este projeto implementa um simulador epidemiológico usando o modelo SEIR (Suscetível-Exposto-Infecioso-Recuperado) com equações diferenciais. Oferece dois métodos de integração numérica: Método de Euler e Método de Runge-Kutta de 4.ª ordem (RK4).

## Requisitos

- Java Development Kit (JDK) 8 ou superior
- Gnuplot (para geração de gráficos)

### Instalar Gnuplot

**macOS** (usando Homebrew):
```bash
brew install gnuplot
```

**Linux**:
```bash
sudo apt-get install gnuplot    # Ubuntu/Debian
sudo dnf install gnuplot        # Fedora
```

**Windows**: Descarregar de [gnuplot.info](http://www.gnuplot.info/download.html)

## Compilar e Executar

### 1. Compilar o projeto

```bash
cd src
javac *.java
```

### 2. Executar em Modo Interativo

```bash
java Main
```

O programa irá pedir:
- Método de integração (1 para Euler, 2 para RK4)
- Nome do ficheiro CSV de entrada (ex: `entrada.csv`)
- Passo de integração (valor entre 0 e 1)
- Tamanho da população
- Número de dias para simulação
- Conjuntos de parâmetros a analisar

### 3. Executar em Modo Linha de Comandos

```bash
java Main entrada.csv -m 2 -p 0.1 -t 1000000 -d 365 output
```

Parâmetros:
- `entrada.csv`: Ficheiro com parâmetros epidemiológicos
- `-m`: Método (1=Euler, 2=RK4)
- `-p`: Passo de integração (0 a 1)
- `-t`: Tamanho da população
- `-d`: Número de dias
- `output`: Nome do ficheiro de saída (sem extensão .csv)

## Formato do Ficheiro de Entrada

Um ficheiro de exemplo `entrada.csv` está incluído. Formato:

```csv
Nome,beta,gama,ro,alfa
Pessoa1,0.5,0.1,0.15,0.2
Pessoa2,0.7,0.2,0.1,0.3
```

Parâmetros:
- `beta`: Taxa de transmissão
- `gama`: Taxa de recuperação
- `ro`: Taxa de exposição
- `alfa`: Taxa de incubação

## Ficheiros de Saída

Todos os ficheiros gerados são guardados em `src/outputs/`:
- **Ficheiros CSV**: Resultados da simulação com dados diários
- **Ficheiros GP**: Scripts do Gnuplot
- **Ficheiros PNG**: Gráficos gerados (em modo não-interativo)

Em modo interativo, os gráficos abrem automaticamente. Também pode visualizá-los manualmente:
```bash
gnuplot -persist src/outputs/nome_ficheiro.gp
```

## Modelo SEIR

A simulação usa estas equações diferenciais:

- dS/dt = -β × S × I / N
- dE/dt = β × S × I / N - α × E
- dI/dt = α × E - γ × I
- dR/dt = γ × I

Onde S=Suscetível, E=Exposto, I=Infecioso, R=Recuperado, N=População total.

## Estrutura do Projeto

- `Main.java`: Ponto de entrada e interface do utilizador
- `Calculos.java`: Métodos de integração numérica (Euler e RK4)
- `OperacaoFicheiros.java`: Operações de leitura/escrita de ficheiros
- `Gnuplot.java`: Geração de gráficos
- `Validacoes.java`: Validação de inputs
