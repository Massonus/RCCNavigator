function uploadProductFile(file, productId) {
    let csrf = document.getElementById("csrf").value;

    let formData = new FormData();
    formData.append("file", file);

    let url = `/upload-product?&productId=${productId}`;

    let response = fetch(url, {
        headers: {
            "X-CSRF-TOKEN": csrf
        },
        method: "POST",
        body: formData
    });
}

function uploadCompanyFile(file, companyId) {
    let csrf = document.getElementById("csrf").value;

    let formData = new FormData();
    formData.append("file", file);

    let url= `/upload-company?companyId=${companyId}`;


    let response = fetch(url, {
        headers: {
            "X-CSRF-TOKEN": csrf
        },
        method: "POST",
        body: formData
    });
}

function checkFile(fileId) {

    let file = fileId === "productFileUpload" ? productFileUpload.files[0] : companyFileUpload.files[0];

    if (!["image/jpeg", "image/png", "image/gif", "image/svg+xml"].includes(file.type)) {
        alert("Only images");
        document.getElementById(fileId).value = '';
        document.querySelector('.input__file-button-text').innerText = 'Choose your file';
        return;
    }

    if (file.size > 1024 * 1024) {
        alert("File must be less then 1 MB");
        document.getElementById(fileId).value = '';
        document.querySelector('.input__file-button-text').innerText = 'Choose your file';
        return;
    }

    document.querySelector('.input__file-button-text').innerText = 'Selected';
}