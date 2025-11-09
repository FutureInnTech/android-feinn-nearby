# Feinn Nearby

`Feinn Nearby` adalah proyek riset dan implementasi komunikasi terenkripsi end-to-end antar perangkat tanpa koneksi internet, dengan memanfaatkan Google Nearby Connections API sebagai media relay data.

## Tujuan

Mewujudkan sistem komunikasi yang:
- 🔐 Aman (End-to-End Encrypted) — setiap pesan dienkripsi sebelum dikirim, dan hanya dapat didekripsi oleh penerima yang sah.
- 🌐 Tanpa Internet — menggunakan peer-to-peer mesh relay berbasis Nearby Connections, bukan jaringan seluler atau Wi-Fi publik.
- 📡 Mandiri & Resilien — tetap dapat berfungsi di area tanpa sinyal, dengan rute komunikasi otomatis melalui perangkat yang berdekatan.
- ⚙️ Efisien & Modular — dapat diintegrasikan ke berbagai aplikasi Android sebagai layer komunikasi private.

## Konsep Dasar

`Feinn Nearby` membangun jaringan lokal ad-hoc antar perangkat Android di sekitar menggunakan Bluetooth, dan Wi-Fi Direct.

Setiap perangkat dapat berperan sebagai:
- Node sumber / pengirim
- Node relay / perantara
- Node penerima akhir

Semua komunikasi dilakukan dengan:
1. Koneksi lokal via Nearby 
2. Pertukaran payload terenkripsi 
3. Dekripsi hanya di perangkat tujuan dengan kunci public

## Prinsip Enkripsi

- Menggunakan kunci publik & privat per perangkat
- Negosiasi kunci awal melalui handshake lokal
- Pesan dienkripsi sebelum dikirim menggunakan AES-GCM
- Payload hanya dapat dibuka oleh penerima yang memiliki kunci privat terkait

## Tujuan Akhir

Menciptakan jaringan komunikasi privat tanpa internet, cocok untuk:
- Situasi darurat atau area tanpa sinyal
- Komunikasi antar-perangkat dalam sistem tertutup
- Aplikasi secure mesh networking di lingkungan industri

## Status Proyek

### Tahap pengembangan awal:
Eksperimen discovery & koneksi antar perangkat melalui Nearby berjalan stabil.

### Tahap selanjutnya:
- Pembuatan service latar belakang communication
- Implementasi lapisan enkripsi dan dekripsi
- Implementasi relay melalui handphone perantara
- Database local communication

## Lisensi

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

> http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```