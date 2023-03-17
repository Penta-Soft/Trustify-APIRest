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
- `solcjs --bin --abi --include-path node_modules/ --base-path . -o src/contract  Trustify-backend/Trustify/contracts/TullioCoin.sol`
- `solcjs --bin --abi --include-path node_modules/ --base-path . -o src/contract  Trustify-backend/Trustify/contracts/ReviewHolder.sol`

I file compilati verranno fuori nella cartella src/contract
Rinominare i file:
- `Trustify-backend_Trustify_contracts_TullioCoin_sol_TullioCoin.bin` in `TullioCoin.bin`
- `Trustify-backend_Trustify_contracts_TullioCoin_sol_TullioCoin.abi` in `TullioCoin.abi`
- `Trustify-backend_Trustify_contracts_ReviewHolder_sol_ReviewHolder.bin` in `ReviewHolder.bin`
- `Trustify-backend_Trustify_contracts_ReviewHolder_sol_ReviewHolder.abi` in `ReviewHolder.abi`

Poi eseguire 
- web3j generate solidity -b src/contract/TullioCoin.bin -a src/contract/TullioCoin.abi -o src/main/java -p com.example.Trustifyrestù
- web3j generate solidity -b src/contract/ReviewHolder.bin -a src/contract/ReviewHolder.abi -o src/main/java -p com.example.Trustifyrest

continua...
