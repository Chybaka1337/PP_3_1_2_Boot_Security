async function getRoles() {
    const response = await fetch("admin/api/roles");

    if (response.ok) {
        return response.json();
    }

    return null;
}

async function getUser() {
    let response = await fetch("admin/api");

    if (!response.ok) {
        response = await fetch("user/api");
    }

    return response.json();
}

async function getUserById(id) {
    const response = await fetch(`admin/api/user/${id}`);

    if (response.ok) {
        return response.json();
    }

    return null;
}

async function getUsers() {
    const response = await fetch("admin/api/users");

    if (response.ok) {
        return response.json();
    }

    return null;
}

async function fillNavInfo() {
    const user = await getUser();
    document.querySelector(".user-info").innerHTML = `${user.username} with roles: ${rolesToString(user.roles)}`;
}

function rolesToString(roles) {
    let str = "";
    for (let role of roles) {
        str += `${role.roleName.slice(5)} `;
    }
    return str;
}

async function fillUsersTable() {
    document.querySelector(".users-table").innerHTML = "";
    const list = await getUsers();
    for (let user of list) {
        let row = document.createElement("tr");
        row.innerHTML = userToTableRow(user);
        row.innerHTML += createButtons(user);
        document.querySelector(".users-table").appendChild(row);
    }
}

async function fillUserTable() {
    document.querySelector(".user-table").innerHTML = "";
    const user = await getUser();
    const row = document.createElement("tr");
    row.innerHTML = userToTableRow(user);
    document.querySelector(".user-table").appendChild(row);
}

function userToTableRow(user) {
    let rowInfo = `<th scope="row">${user.id}</th>`;
    for (let key in user) {
        if (key !== "id" && key !== "password" && key !== "roles") {
            let property = user[key] ? user[key] : "";
            rowInfo += `<td>${property}</td>`;
        } else if (key === "roles") {
            rowInfo += `<td>${rolesToString(user[key])}</td>`;
        }
    }
    return rowInfo;
}

function createButtons(user) {
    const editButton = `<a class="btn btn-primary"
                            onclick="fillModalForm(${user.id})"
                            href="#editModal"
                            type="button"
                            data-bs-toggle="modal">Edit</a>`
    const deleteButton = `<a class="btn btn-danger"
                            onclick="deleteUser(${user.id})"
                            role="button">Delete</a>`
    return `<td>${editButton}</td>` + `<td>${deleteButton}</td>`
}

async function deleteUser(id) {
    const response = await fetch(`admin/api/delete/${id}`)
    fillUsersTable();
}

// TODO: сделать форму редактирования пользователя
//  UPDATE: добавить поле роль
async function fillModalForm(id) {
    let user = await getUserById(id);
    let modalBody = document.querySelector(".modal-body");
    modalBody.innerHTML = "";
    for (let key in user) {
        modalBody.appendChild(createDivForForm(user, key));
    }
    modalBody.appendChild(await divForRoles());
}

// TODO: сделать форму отправки пользователя
async function createUserForm() {
    const user = {
        username : null,
        firstName : null,
        lastName : null,
        age : null,
        roles : null,
        password : null
    };
    let div = document.querySelector(".general");
    div.innerHTML = "";
    for (let key in user) {
        div.appendChild(createDivForForm(user, key));
    }
    div.appendChild(await divForRoles());
}

function createDivForForm(user, key) {
    let div = document.createElement("div");
    if (key !== "roles") {
        div.classList.add("form-floating" , "mb-3", "ml-3");
        div.innerHTML = `<input type="${getType(user, key)}"
                                class="form-control"
                                ${key === "id" ? "readonly" : ""}
                                id="${key}"
                                placeholder="${key}"
                                name="${key}"
                                value="${getValue(user, key)}">
                         <label for="${key}">${key}</label>`;
    }
    return div;
}

function getType(user, key) {
    if (typeof user[key] === "number") {
        return "number;"
    } else {
        return key === "password" ? "password" : "text";
    }
}

function getValue(user, key) {
    if (key !== "password") {
        return user[key] !== null ? user[key] : "";
    }
    return "";
}

async function submit(event, form, user, HTTP) {
    event.preventDefault();
    user.roles = getSelectedRoles(form);
    console.log(user);
    await upload(HTTP, user);
    fillUsersTable();
}

async function editSubmit(event) {
    const user = getUserFromForm(this);
    await submit(event, this, user,`admin/api/user/${user.id}`);
    await fillNavInfo();
    bootstrap.Modal.getInstance(document.querySelector(".modal")).hide();
}

async function createSubmit(event) {
    const user = getUserFromForm(this);
    await submit(event, this, user,"admin/api/create");
    bootstrap.Tab.getInstance(document.getElementById("table-tab")).show();
}

function getUserFromForm(form) {
    const formData = new FormData(form);
    return Object.fromEntries(formData);
}

function upload(HTTP, user) {
    return fetch(HTTP, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    });
}

async function divForRoles() {
    const roles = await getRoles();
    console.log(roles);
    const div = document.createElement("div");
    const ul = document.createElement("ul");
    ul.classList.add("list-group");
    for (let role of roles) {
        ul.innerHTML += `<li class="list-group-item">
                            <input class="form-check-input me-1" 
                            type="checkbox" 
                            id="checkbox${role.roleName}"
                            name="roles"
                            value="${role.id}">
                            <label class="form-check-label" for="checkbox${role.roleName}">${role.roleName.slice(5)}</label>
                        </li>`;
    }
    div.appendChild(ul);
    return div;
}

function getSelectedRoles(form) {
    const inputs = form.getElementsByTagName("input");
    const roles = [];
    for (let input of inputs) {
        if (input.type === "checkbox" && input.checked) {
            let role = {
                id : input.value,
                roleName : input.id.slice(8)
            }
            roles.push(role);
        }
    }
    return roles;
}


