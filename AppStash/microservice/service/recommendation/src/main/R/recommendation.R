#!/usr/bin/env Rscript
library('rjson')
library('recommenderlab')

args <- commandArgs(TRUE)
## Default setting when no arguments passed
if(length(args) < 2) {
  args <- c("--help")
}

## Help section
if("--help" %in% args) {
  cat("

Create recommendations from csv file

userId,articleId,preference
53cbaf81ef86693b259ad5e2,102,153
53cbaf81ef86693b259ad5ec,305,1
53cbaf81ef86693b259ad5cb,100,121

./recommendation.R user-item-pref.csv recommendation.json \n\n")

  q(save="no")
}

srcFile <- as.character(args[1])
outFile <- as.character(args[2])

csv = read.csv(srcFile, sep=",", header = TRUE)
ratingMatrix <- as(csv, "realRatingMatrix")
userIds <- dimnames(ratingMatrix)[[1]]
recommender <- Recommender(ratingMatrix, method="POPULAR")
recommendations <- predict(recommender, ratingMatrix, n=3)
recommendationsAsList <- as(recommendations, "list")
frame <- data.frame(recommendationsAsList)
colnames(frame) <- userIds
json <- toJSON(frame)
write(json, file = outFile)