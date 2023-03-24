# MyJob v.1.0
Aplikasi pencari jasa freelance

## Fitur:
## Rest API And Dashboard Administrator :
- Bahasa Pemrograman PHP
- Laravel versi 9.52.4
- Yajra Databale : 10.3.1
- template Rocker

##  Android:
- Bahasa Pemrograman Java
- Target API : 33

## Installasi:
## Web Administrator dan Rest API
- Buka folder "RestAPI/MyJob"
- buat database kosong dengan nama: myjob
- Copas file : .env.example dan rename menjadi .env
- edit file .env dan lakukan konfigurasi database dan ganti nama database dengan nama : myjob
- ketik di terminal: composer install
- ketik di terminal: php artisan key:generate
- ketik di terminal: php artisan migrate:fresh --seed
- ketik di terminal: php artisan serve
