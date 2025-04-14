# Seguradora ACME

## Cenários de Testes

### Criar Cotação

#### 🟢 Cotação criada com sucesso

Produto e oferta ativos, todos os dados válidos.

##### Request:

```json
{
    "productId": "1b2da7cc-b367-4196-8a78-9cfeec21f587",
    "offerId": "adc56d77-348c-4bf0-908f-22d402ee715c",
    "category": "HOME",
    "totalMonthlyPremiumAmount": 75.25,
    "totalCoverageAmount": 825000.00,
    "coverages": {
        "Incêndio": 250000.00,
        "Desastres naturais": 500000.00,
        "Responsabiliadade civil": 75000.00
    },
    "assistances": [
        "Encanador",
        "Eletricista",
        "Chaveiro 24h"
    ],
    "customer": {
        "documentNumber": "36205578900",
        "name": "John Wick",
        "type": "NATURAL",
        "gender": "MALE",
        "dateOfBirth": "1973-05-02",
        "email": "johnwick@gmail.com",
        "phoneNumber": 11950503030
    }
}
```

##### Expectativa:

- Status 201 Created 
- Cotação persistida 
- Mensagem publicada no Kafka (QuotationCreatedEvent)

#### 🔴 Produto inativo

Trocar o `productId` por um inativo.

##### Request:

```json
{
    "productId": "edaa71d6-4f31-4f6a-a3cb-59949d48ad5e",
    "offerId": "adc56d77-348c-4bf0-908f-22d402ee715c",
    "category": "HOME",
    "totalMonthlyPremiumAmount": 75.25,
    "totalCoverageAmount": 825000.00,
    "coverages": {
        "Incêndio": 250000.00,
        "Desastres naturais": 500000.00,
        "Responsabiliadade civil": 75000.00
    },
    "assistances": [
        "Encanador",
        "Eletricista",
        "Chaveiro 24h"
    ],
    "customer": {
        "documentNumber": "36205578900",
        "name": "John Wick",
        "type": "NATURAL",
        "gender": "MALE",
        "dateOfBirth": "1973-05-02",
        "email": "johnwick@gmail.com",
        "phoneNumber": 11950503030
    }
}
```

##### Resposta esperada:

```json
{
    "code": "422",
    "message": "Product is inactive"
}
```

#### 🔴 Produto não encontrado

Trocar o `productId` por um produto não existente.

##### Request:

```json
{
    "productId": "00000000-0000-0000-0000-000000000000",
    "offerId": "adc56d77-348c-4bf0-908f-22d402ee715c",
    "category": "HOME",
    "totalMonthlyPremiumAmount": 75.25,
    "totalCoverageAmount": 825000.00,
    "coverages": {
        "Incêndio": 250000.00,
        "Desastres naturais": 500000.00,
        "Responsabiliadade civil": 75000.00
    },
    "assistances": [
        "Encanador",
        "Eletricista",
        "Chaveiro 24h"
    ],
    "customer": {
        "documentNumber": "36205578900",
        "name": "John Wick",
        "type": "NATURAL",
        "gender": "MALE",
        "dateOfBirth": "1973-05-02",
        "email": "johnwick@gmail.com",
        "phoneNumber": 11950503030
    }
}
```

##### Resposta esperada:

```json
{
    "code": "404",
    "message": "Product with ID [00000000-0000-0000-0000-000000000000] not found"
}
```

#### 🔴 Oferta inativa

Trocar o `offerId` por um inativo.

##### Request:

```json
{
    "productId": "1b2da7cc-b367-4196-8a78-9cfeec21f587",
    "offerId": "b56de912-cd30-4795-962e-07327eceba5c",
    "category": "HOME",
    "totalMonthlyPremiumAmount": 75.25,
    "totalCoverageAmount": 825000.00,
    "coverages": {
        "Incêndio": 250000.00,
        "Desastres naturais": 500000.00,
        "Responsabiliadade civil": 75000.00
    },
    "assistances": [
        "Encanador",
        "Eletricista",
        "Chaveiro 24h"
    ],
    "customer": {
        "documentNumber": "36205578900",
        "name": "John Wick",
        "type": "NATURAL",
        "gender": "MALE",
        "dateOfBirth": "1973-05-02",
        "email": "johnwick@gmail.com",
        "phoneNumber": 11950503030
    }
}
```

##### Resposta esperada:

```json
{
    "code": "422",
    "message": "Offer is inactive"
}
```

#### 🔴 Oferta não encontrada

Trocar `offerId` por uma oferta não existente.

##### Request:

```json
{
    "productId": "1b2da7cc-b367-4196-8a78-9cfeec21f587",
    "offerId": "00000000-0000-0000-0000-000000000000",
    "category": "HOME",
    "totalMonthlyPremiumAmount": 75.25,
    "totalCoverageAmount": 825000.00,
    "coverages": {
        "Incêndio": 250000.00,
        "Desastres naturais": 500000.00,
        "Responsabiliadade civil": 75000.00
    },
    "assistances": [
        "Encanador",
        "Eletricista",
        "Chaveiro 24h"
    ],
    "customer": {
        "documentNumber": "36205578900",
        "name": "John Wick",
        "type": "NATURAL",
        "gender": "MALE",
        "dateOfBirth": "1973-05-02",
        "email": "johnwick@gmail.com",
        "phoneNumber": 11950503030
    }
}
```

##### Resposta esperada:

```json
{
    "code": "404",
    "message": "Offer with ID [00000000-0000-0000-0000-000000000000] not found"
}
```

#### 🔴 Oferta não pertence ao produto

Trocar `offerId` por uma oferta válida, mas que pertence a outro produto.

##### Request:

```json
{
    "productId": "1b2da7cc-b367-4196-8a78-9cfeec21f587",
    "offerId": "b56de912-cd30-4795-962e-07327eceba5c",
    "category": "HOME",
    "totalMonthlyPremiumAmount": 75.25,
    "totalCoverageAmount": 825000.00,
    "coverages": {
        "Incêndio": 250000.00,
        "Desastres naturais": 500000.00,
        "Responsabiliadade civil": 75000.00
    },
    "assistances": [
        "Encanador",
        "Eletricista",
        "Chaveiro 24h"
    ],
    "customer": {
        "documentNumber": "36205578900",
        "name": "John Wick",
        "type": "NATURAL",
        "gender": "MALE",
        "dateOfBirth": "1973-05-02",
        "email": "johnwick@gmail.com",
        "phoneNumber": 11950503030
    }
}
```

##### Resposta esperada:

```json
{
    "code": "422",
    "message": "Offer does not belong to the specified product"
}
```

#### 🔴 Cobertura inválida (não está no catálogo)

Adicionar uma cobertura que não existe na oferta.

##### Request:

```json
{
    "productId": "1b2da7cc-b367-4196-8a78-9cfeec21f587",
    "offerId": "adc56d77-348c-4bf0-908f-22d402ee715c",
    "category": "HOME",
    "totalMonthlyPremiumAmount": 75.25,
    "totalCoverageAmount": 825000.00,
    "coverages": {
        "Incêndio": 250000.00,
        "Desastres naturais": 500000.00,
        "Cobertura inventada": 1000.00
    },
    "assistances": [
        "Encanador",
        "Eletricista",
        "Chaveiro 24h"
    ],
    "customer": {
        "documentNumber": "36205578900",
        "name": "John Wick",
        "type": "NATURAL",
        "gender": "MALE",
        "dateOfBirth": "1973-05-02",
        "email": "johnwick@gmail.com",
        "phoneNumber": 11950503030
    }
}
```

##### Resposta esperada:

```json
{
    "code": "422",
    "message": "Coverage [Cobertura inventada] is not available in the offer"
}
```

#### 🔴 Cobertura acima do máximo permitido

Exagerar o valor de uma cobertura.

##### Request:

```json
{
    "productId": "1b2da7cc-b367-4196-8a78-9cfeec21f587",
    "offerId": "adc56d77-348c-4bf0-908f-22d402ee715c",
    "category": "HOME",
    "totalMonthlyPremiumAmount": 75.25,
    "totalCoverageAmount": 825000.00,
    "coverages": {
        "Incêndio": 9999999.00,
        "Desastres naturais": 200000.00,
        "Responsabiliadade civil": 100000.00
    },
    "assistances": [
        "Encanador",
        "Eletricista",
        "Chaveiro 24h"
    ],
    "customer": {
        "documentNumber": "36205578900",
        "name": "John Wick",
        "type": "NATURAL",
        "gender": "MALE",
        "dateOfBirth": "1973-05-02",
        "email": "johnwick@gmail.com",
        "phoneNumber": 11950503030
    }
}
```

##### Resposta esperada:

```json
{
    "code": "422",
    "message": "Coverage [Incêndio] exceeds max allowed value"
}
```

#### 🔴 Assistência inválida (não está no catálogo)

Adicionar uma assistência que não existe na oferta.

##### Request:

```json
{
    "productId": "1b2da7cc-b367-4196-8a78-9cfeec21f587",
    "offerId": "adc56d77-348c-4bf0-908f-22d402ee715c",
    "category": "HOME",
    "totalMonthlyPremiumAmount": 75.25,
    "totalCoverageAmount": 825000.00,
    "coverages": {
        "Incêndio": 250000.00,
        "Desastres naturais": 500000.00,
        "Responsabiliadade civil": 75000.00
    },
    "assistances": [
        "Assistência Inexistente"
    ],
    "customer": {
        "documentNumber": "36205578900",
        "name": "John Wick",
        "type": "NATURAL",
        "gender": "MALE",
        "dateOfBirth": "1973-05-02",
        "email": "johnwick@gmail.com",
        "phoneNumber": 11950503030
    }
}
```

##### Resposta esperada:

```json
{
    "code": "422",
    "message": "Assistance [Assistência Inexistente] is not available in the offer"
}
```

#### 🔴 Prêmio fora da faixa da oferta

##### Request:

```json
{
    "productId": "1b2da7cc-b367-4196-8a78-9cfeec21f587",
    "offerId": "adc56d77-348c-4bf0-908f-22d402ee715c",
    "category": "HOME",
    "totalMonthlyPremiumAmount": 101.00,
    "totalCoverageAmount": 825000.00,
    "coverages": {
        "Incêndio": 250000.00,
        "Desastres naturais": 500000.00,
        "Responsabiliadade civil": 75000.00
    },
    "assistances": [
        "Encanador",
        "Eletricista",
        "Chaveiro 24h"
    ],
    "customer": {
        "documentNumber": "36205578900",
        "name": "John Wick",
        "type": "NATURAL",
        "gender": "MALE",
        "dateOfBirth": "1973-05-02",
        "email": "johnwick@gmail.com",
        "phoneNumber": 11950503030
    }
}
```

##### Resposta esperada:

```json
{
    "code": "422",
    "message": "Total monthly premium amount is outside the allowed range"
}
```

#### 🔴 Total de cobertura não bate com a soma

##### Request:

```json
{
    "productId": "1b2da7cc-b367-4196-8a78-9cfeec21f587",
    "offerId": "adc56d77-348c-4bf0-908f-22d402ee715c",
    "category": "HOME",
    "totalMonthlyPremiumAmount": 75.25,
    "totalCoverageAmount": 700000.00,
    "coverages": {
        "Incêndio": 250000.00,
        "Desastres naturais": 500000.00
    },
    "assistances": [
        "Encanador",
        "Eletricista",
        "Chaveiro 24h"
    ],
    "customer": {
        "documentNumber": "36205578900",
        "name": "John Wick",
        "type": "NATURAL",
        "gender": "MALE",
        "dateOfBirth": "1973-05-02",
        "email": "johnwick@gmail.com",
        "phoneNumber": 11950503030
    }
}
```

##### Resposta esperada:

```json
{
    "code": "422",
    "message": "Total coverage amount does not match the sum of individual coverages"
}
```

#### 🔴 Campos obrigatórios ausentes

##### Request:

```json
{
    "productId": null,
    "offerId": "adc56d77-348c-4bf0-908f-22d402ee715c",
    "category": "HOME",
    "totalMonthlyPremiumAmount": 75.25,
    "totalCoverageAmount": 825000.00,
    "coverages": {
        "Incêndio": 250000.00,
        "Desastres naturais": 500000.00,
        "Responsabiliadade civil": 75000.00
    },
    "assistances": [
        "Encanador",
        "Eletricista",
        "Chaveiro 24h"
    ],
    "customer": {
        "documentNumber": "36205578900",
        "name": "John Wick",
        "type": "NATURAL",
        "gender": "MALE",
        "dateOfBirth": "1973-05-02",
        "email": "johnwick@gmail.com",
        "phoneNumber": 11950503030
    }
}
```

##### Resposta esperada:

```json
{
    "code": "400",
    "message": "These attributes are missing or are invalid",
    "errors": [
        {
            "field": "productId",
            "description": "Product ID is required"
        }
    ]
}
```

### 🔍 Consultar Cotação por ID

#### 🟢 Cotação encontrada com sucesso

Depois de criar uma cotação com sucesso, realiza a consulta utilizando o id retornado.

##### Request:

GET /quotations/{id}

##### Resposta esperada:

```json
{
    "id": 1,
    "insurancePolicyId": null,
    "productId": "1b2da7cc-b367-4196-8a78-9cfeec21f587",
    "offerId": "adc56d77-348c-4bf0-908f-22d402ee715c",
    "category": "HOME",
    "createdAt": "2024-05-22T20:37:17.090098",
    "updatedAt": "2024-05-22T21:05:02.090098",
    "totalMonthlyPremiumAmount": 75.25,
    "totalCoverageAmount": 825000.00,
    "coverages": {
        "Incêndio": 250000.00,
        "Desastres naturais": 500000.00,
        "Responsabiliadade civil": 75000.00
    },
    "assistances": [
        "Encanador",
        "Eletricista",
        "Chaveiro 24h"
    ],
    "customer": {
        "documentNumber": "36205578900",
        "name": "John Wick",
        "type": "NATURAL",
        "gender": "MALE",
        "dateOfBirth": "1973-05-02",
        "email": "johnwick@gmail.com",
        "phoneNumber": 11950503030
    }
}
```

#### 🔴 Cotação não encontrada

Usar um ID inexistente.

##### Request:

GET /quotations/00000000-0000-0000-0000-000000000000

##### Resposta esperada:

```json
{
    "code": "404",
    "message": "Quotation with ID [00000000-0000-0000-0000-000000000000] not found"
}
```