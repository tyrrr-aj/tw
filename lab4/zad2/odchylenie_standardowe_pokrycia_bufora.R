buffer_size <- 1000
experiment_size = 1000000

modifications <- sample(-(buffer_size%/%2) : (buffer_size%/%2), experiment_size, replace=T)
buffer_coverage <- rep(0, experiment_size + 1)

for (i in seq(1, experiment_size)) {
  if (buffer_coverage[i] + modifications[i] >= 0 && buffer_coverage[i] + modifications[i] <= buffer_size)
    buffer_coverage[i+1] = buffer_coverage[i] + modifications[i]
  else
    buffer_coverage[i+1] = buffer_coverage[i]
}

print(sd(buffer_coverage))
print(mean(buffer_coverage))