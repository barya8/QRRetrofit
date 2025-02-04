---

# QR Retrofit SDK

The **QR Retrofit SDK** is a lightweight library designed to simplify interactions with the QR Code & Barcode API. It provides easy-to-use methods for generating, scanning, and managing QR codes and barcodes. Built on top of Retrofit, it handles API requests and responses efficiently.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
  - [Initialization](#initialization)
  - [API Methods](#api-methods)
- [Version Settings](#version-settings)
- [License](#license)

---

## Features

- **QR Code Generation**: Generate QR codes with customizable parameters.
- **QR Code Management**: Retrieve, update, and delete QR code data.
- **QR Code Scanning**: Scan and validate QR codes and barcodes.
- **Error Handling**: Built-in error handling for API responses.
- **Retrofit Integration**: Leverages Retrofit for efficient network communication.

---

## Installation

Add the following dependencies to your `build.gradle` file:

```gradle
dependencies {
    // SDK
    implementation("com.example.qrretrofit:sdk:2.0.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
}
```

---

## Usage

### Initialization

To start using the SDK, initialize the `GenericController` with the base URL of the API and a callback interface:

```java
String baseUrl = "https://your-api-endpoint.com/";
GenericCallback callback = new GenericCallback() {
    @Override
    public void success(String response) {
        // Handle successful response
        System.out.println("Success: " + response);
    }

    @Override
    public void error(String error) {
        // Handle error
        System.err.println("Error: " + error);
    }
};

GenericController controller = new GenericController(baseUrl, callback);
```

### API Methods

#### 1. Retrieve All Data
Fetch all data stored in Firebase Realtime Database:

```java
String apiKey = "your-api-key";
controller.getAllDataImpl(apiKey, callback);
```

#### 2. Retrieve Data by Client
Fetch data for a specific client using their API key:

```java
controller.getDataByClientImpl(apiKey, callback);
```

#### 3. Delete QR Code by ID
Delete a specific QR code by its ID:

```java
int qrId = 123; // Example QR code ID
controller.deleteQrByIdImpl(apiKey, qrId, callback);
```

#### 4. Delete All Data
Delete all data for a specific API key:

```java
controller.deleteAllImpl(apiKey, callback);
```

#### 5. Generate QR Code
Generate a QR code with customizable parameters:

```java
Map<String, String> params = new HashMap<>();
params.put("type", "2"); // QR code type
params.put("url", "https://example.com"); // URL to encode
params.put("size", "150"); // QR code size
params.put("errorCorrection", "H"); // Error correction level

controller.generateQRCodeImpl(apiKey, params, callback);
```

#### 6. Update QR Code by ID
Update an existing QR code by its ID:

```java
Map<String, String> params = new HashMap<>();
params.put("id", "123"); // QR code ID
params.put("url", "https://updated-url.com"); // New URL
params.put("size", "200"); // New size

controller.updateQrByIdImpl(apiKey, params, callback);
```

#### 7. Scan QR Code
Scan a QR code from an image file:

```java
File file = new File("path/to/qr-code-image.png");
MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(file, MediaType.parse("image/png")));

controller.scanQRCodeImpl(apiKey, filePart, callback);
```

#### 8. Scan Barcode
Scan a barcode from an image file:

```java
controller.scanBarcodeImpl(apiKey, filePart, callback);
```

---

## Version Settings

The SDK uses the following versions of dependencies:

```gradle
qrretrofit_version = "2.0.0"
retrofit = "2.11.0"
```

Ensure compatibility by using the specified versions of Retrofit and Gson converter.

---

## License

This SDK is licensed under the **Apache License 2.0**. See the [LICENSE](LICENSE) file for details.

---

## Support

For questions or issues, please contact [Bar Yaron](mailto:bar.yaron@s.afeka.ac.il).

---


[![](https://jitpack.io/v/barya8/QRRetrofit.svg)](https://jitpack.io/#barya8/QRRetrofit)
