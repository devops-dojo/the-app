server {
    listen   8081; ## listen for ipv4; this line is default and implied
    #listen   [::]:80 default ipv6only=on; ## listen for ipv6

    # Make site accessible from http://localhost/
    server_name registration.microservice.io;

    location / {
        rewrite /shop/(.*)  /shop/$1            break;
        rewrite /           /shop/registration  permanent;
        rewrite /(.*)       /shop/$1            break;

        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

        proxy_pass http://app-server-node-2:8080;
    }
}
