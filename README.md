# Trustify-APIRest

## Submodules
Se è la prima volta che clonate la repo allora dovete fare:
- clonare la repo APIrest
- eseguire il comando `git submodule update --init --recursive`

Per fare l'update dei file di backend fare:
- `git pull --recurse-submodules`

Per provare L'api rest bisogna:
- Avere intellij installato (ho usato quello)
- aprire il progetto con intellij, e installare le dipendenze con maven a destra del programma
su Trustify-rest/lyfecycle e install
- accendere ganache in un terminale `npx ganache`
- Avviare visual studio code nella cartella backend dentro il progetto trustify-api
- eseguire i seg comandi: `cd trustify` `npx truffle test test/APIREST.js`
se tutto va bene troverete una stringa del genere:

`Contract: RESTAPI  
contract address: 0xe5C6b5AbE59b070662Dd356F76cc68253b982C7a  
company address: 0x32319b181DfFfebb7469eF56893BaA9EFF023cFf` 

- prendete il contract address e lo incollate dentro la classe java TrustifyAPIREST
- prendete il company address e lo incollate nell'url da chiamare
 - `http://localhost:8080/reviews?address=0xQUALCOSA&startRange=0&endRange=9`
Lo start range e l'end range sono il range di review che vuoi visualizzare (parte da 0) 

- per runnare l'app vai sulla classe TrustifyRestApplication e in alto a destra premi il pulsante play



