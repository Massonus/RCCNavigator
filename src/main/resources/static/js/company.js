function createCompany(event) {
    event.preventDefault();

    let csrf = document.getElementById("csrf").value;

    let title = document.getElementById("companyTitle").value;
    let priceCategory = document.getElementById("priceCategory").value;
    let countryId = document.getElementById("companyCountry").value;
    let categoryId = document.getElementById("kitchenCategory").value;
    let imageLink = document.getElementById("companyImageLink").value;

    let file = companyFileUpload.files[0];

    if (file === undefined && imageLink.trim() === "") {
        document.getElementById("companyImageError").textContent = "Please input image link or upload your file";
        return;
    }

    const body = JSON.stringify({
        title: title,
        priceCategory: priceCategory,
        countryId: countryId,
        categoryId: categoryId,
        imageLink: imageLink
    });

    const url = "/company/add";

    fetch(url, {
        method: "POST",
        redirect: 'follow',
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrf,
        },
        body: body,

    })
        .then(res => {

            if (imageLink.trim() === "") {
                uploadCompanyFile(file, title, undefined);
                window.location.href = `/admin/panel`;
            } else {
                window.location.href = `/admin/panel`;
            }

        })
        .catch(error => {
            console.log(error);
        })
}


function editCompany(event, companyId) {
    event.preventDefault();

    let csrf = document.getElementById("csrf").value;

    let title = document.getElementById("companyTitle").value;
    let priceCategory = document.getElementById("priceCategory").value;
    let countryId = document.getElementById("companyCountry").value;
    let categoryId = document.getElementById("kitchenCategory").value;
    let imageLink = document.getElementById("companyImageLink").value;

    let file = companyFileUpload.files[0];

    const body = JSON.stringify({
        companyId: companyId,
        title: title,
        priceCategory: priceCategory,
        countryId: countryId,
        categoryId: categoryId,
        imageLink: imageLink
    });

    const url = "/company/edit";

    fetch(url, {
        method: "PUT",
        redirect: 'follow',
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrf,
        },
        body: body,

    })
        .then(res => {

            if (!(file === undefined)) {
                uploadCompanyFile(file, undefined, companyId);
                window.location.href = `/admin/panel`;
            } else {
                window.location.href = `/admin/panel`;
            }

        })
        .catch(error => {
            console.log(error);
        })
}

function deleteCompany(companyId, csrf) {

    const url = `/company/delete?companyId=${companyId}`;

    fetch(url, {
        method: "DELETE",
        headers: {
            'X-CSRF-TOKEN': csrf,
        },
    })
        .then(res => {
            document.getElementById(`company-table-${companyId}`).remove();
        })
        .catch(error =>
            console.error(error));
}