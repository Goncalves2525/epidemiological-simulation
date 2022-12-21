set output' ruca .png'
set title 'Distribuicao da falsa noticia(euler)'
set xlabel 'Numero dias'
set ylabel 'Populacao'
set xrange [1:30]
set yrange [1:1000]
set grid
set datafile separator ";"
plot for [col=2:4] 'Ruca.csv' using 0:col with lines title columnheader
