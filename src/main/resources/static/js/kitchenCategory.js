function createKitchenCategory(event) {
    event.preventDefault();

    let csrf = document.getElementById("csrf").value;
    let title = document.getElementById("categoryTitle").value.toUpperCase();

    const body = JSON.stringify({
        title: title
    });

    const url = "/category/add";

    fetch(url, {
        method: "POST",
        redirect: 'follow',
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrf,
        },
        body: body,
    })
        .then(res => res.json())
        .then((data) => {
            console.log(data.title);
            window.location.href = "/admin/panel";
        })
        .catch(error => {
            console.log(error);
        })
}

function editKitchenCategory(event, categoryId) {
    event.preventDefault();

    let csrf = document.getElementById("csrf").value;
    let title = document.getElementById("categoryTitle").value.toUpperCase();

    const body = JSON.stringify({
        categoryId: categoryId,
        title: title
    });

    const url = `/category/edit`;

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
            window.location.href = "/admin/panel";
        })
        .catch(error => {
            console.log(error);
        })
}

function deleteKitchenCategory(categoryId, csrf) {

    const url = `/category/delete?id=${categoryId}`;

    fetch(url, {
        method: "DELETE",
        headers: {
            'X-CSRF-TOKEN': csrf,
        },
    })
        .then(res => {
            document.getElementById(`category-table-${categoryId}`).remove();
        })
        .catch(error =>
            console.error(error));
}

