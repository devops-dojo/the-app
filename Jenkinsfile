
node {
   stage 'Checkout'
    checkout scm
   
   stage 'Build and Deploy'
     echo 'Build and deploy in progress'
     sh "cd compose && docker-compose up -d"
}
