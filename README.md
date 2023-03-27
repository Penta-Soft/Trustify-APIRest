# Trustify-APIRest

## Submodules
Se è la prima volta che clonate la repo allora dovete fare:
- clonare la repo APIrest
- eseguire il comando `git submodule update --init --recursive`

Per fare l'update dei file di backend fare:
- `git pull --recurse-submodules`

## npm
eseguire un `npm install` per installare le dependencies

## Compilare i contratti per avere i .bin e .abi
- `solcjs --bin --abi --include-path node_modules/ --base-path . -o src/contract  Trustify-backend/Trustify/contracts/TCoin.sol`
- `solcjs --bin --abi --include-path node_modules/ --base-path . -o src/contract  Trustify-backend/Trustify/contracts/Trustify.sol`

I file compilati verranno fuori nella cartella src/contract
Rinominare i file:
- `Trustify-backend_Trustify_contracts_TullioCoin_sol_TullioCoin.bin` in `TCoin.bin`
- `Trustify-backend_Trustify_contracts_TullioCoin_sol_TullioCoin.abi` in `TCoin.abi`
- `Trustify-backend_Trustify_contracts_ReviewHolder_sol_ReviewHolder.bin` in `Trustify.bin`
- `Trustify-backend_Trustify_contracts_ReviewHolder_sol_ReviewHolder.abi` in `Trustify.abi`

Poi eseguire 
- `web3j generate solidity -b src/contract/TCoin.bin -a src/contract/TCoin.abi -o src/main/java -p com.example.Trustifyrest`
- `web3j generate solidity -b src/contract/Trustify.bin -a src/contract/Trustify.abi -o src/main/java -p com.example.Trustifyrest`

Da questo momento dovrebbero essere comparse delle classi Trustify.java e TCoin.java

per runnare i test usate maven
prima di tutto però bisogna cambiare la chiave di customerAccountCredential con la prima di ganache dentro il file di test
