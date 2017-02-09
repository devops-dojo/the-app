
upstream _Site_A {
  server shop.microservice.io:81;
}

upstream _Site_B {
  server shop.microservice.io:82;
}

split_clients "${time_local}AAA" $variant {
  99%   _Site_A;
  1%   _Site_B;
}

server {
    listen   80; ## listen for ipv4; this line is default and implied
    #listen   [::]:80 default ipv6only=on; ## listen for ipv6

    root /usr/share/shop/frontend/catalog/html;
    index index.html index.htm;

    # Make site accessible from http://localhost/
    server_name shop.microservice.io;

    location / {
      proxy_set_header Host $host;
      proxy_pass http://${variant};
      add_header Cache-Control no-cache;
      expires off;
    }

    include proxy/shop-routes.conf;
}
