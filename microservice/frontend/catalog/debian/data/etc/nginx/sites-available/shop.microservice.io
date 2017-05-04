upstream _Site_A {
  server localhost:81;
}

upstream _Site_B {
  server localhost:82;
}

split_clients "${time_local}AAA" $variant {
  80%   _Site_A;
  20%   _Site_B;
}

server {
    listen   8081; ## listen for ipv4; this line is default and implied

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
