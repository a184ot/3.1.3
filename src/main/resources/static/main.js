document.body.addEventListener("click", function (evt) {
    //create new user vvv
    if(evt.target.getAttribute("name") === "create") {
        evt.preventDefault();
        evt.stopPropagation();
        createUser();
    }
    // fill delete modal
    if(evt.target.getAttribute("data-target") === "#deleteModal") {
        let userId = evt.target.getAttribute("id");
        fillDeleteModal(userId);
    }
    // delete user vvv
    if(evt.target.getAttribute("id") === "btnDeliteId") {
        deleteUser();
    }
    // fill edit modal
    if(evt.target.getAttribute("data-target") === "#editModal") {
        let userId = evt.target.getAttribute("id");
        fillEditModal(userId);
    }
    // edit user vvv
    if(evt.target.getAttribute("id") === "btnEditId") {
        editUser();
    }
})

function createUser() {
    let form = document.getElementById("createUserForm");
    let inputs = form.querySelectorAll("input");
    let sendData = {};
    for(let i=0; i<inputs.length; i++) {
        sendData[inputs[i].id] = inputs[i].value;
    }
    let select = form.querySelector("select");
    let options = select.querySelectorAll("option");
    let sendDataRole = [];
    for (let i = 0; i < options.length; i++) {
        if(options[i].selected){
            sendDataRole.push(options[i].value)
        }
    }
    sendData["role"] = sendDataRole;

    fetch("/rest/",{
        method: "POST",
        headers: {
            'Content-Type': 'application/json;charset= utf-8'
        },
        body:JSON.stringify(sendData)
    }).then(response=>response.json()).then(res => addUserLine(res)).
    then(addUserClose).then(clearAddingForm).then(console.log("createUser"));
}

function clearAddingForm() {
    let formForClear = document.getElementById("createUserForm").childNodes.item(3).childNodes;
    for (let i = 1; i<10; i=i+2) {
        formForClear.item(i).childNodes.item(3).value="";
    }
    formForClear.item(11).childNodes.item(3).childNodes.item(1).selected=false
    formForClear.item(11).childNodes.item(3).childNodes.item(3).selected=false;
}

function addUserLine(res) {
    let hiddenUser = document.getElementById("hiddenLine");
    let cloneLine = hiddenUser.cloneNode(true);
    hiddenUser.before(cloneLine);
    let lineUser = document.getElementById("hiddenLine");
    lineUser.hidden = false;
    lineUser.id = `user${res.id}`;
    changeUserField(res)
}

function changeUserField(changedUser) {
    let formChange = document.getElementById(`user${changedUser.id}`);
    let doc2 = formChange.childNodes;
    doc2.item(1).textContent = changedUser.id;
    doc2.item(3).textContent = changedUser.firstName;
    doc2.item(5).textContent = changedUser.lastName;
    doc2.item(7).textContent = changedUser.age;
    doc2.item(9).textContent = changedUser.email;
    let roleUser = changedUser.role;
    let role1 = doc2.item(11).childNodes.item(2);
    let role2 = doc2.item(11).childNodes.item(3);
    if (roleUser[1].name.includes("ADMIN")) {
        role2.textContent = "ADMIN";
    } else {
        role2.textContent = "USER";
    }
    if (roleUser[0] != null) {
        if (roleUser[0].name.includes("ADMIN")) {
            role1.textContent = "ADMIN";
        } else {
            role1.textContent = "USER";
        }
    }
    doc2.item(13).childNodes.item(1).id=`${changedUser.id}`;
    doc2.item(15).childNodes.item(1).id=`${changedUser.id}`
}

function addUserClose() {
    let tab = document.getElementById("v-pills-create-tab");
    let usersTab = document.getElementById("v-pills-home2-tab");
    tab.setAttribute("aria-selected", false);
    usersTab.setAttribute("aria-selected", true);
    document.getElementById("v-pills-home").classList.add("show", "active");
    document.getElementById("v-pills-create").classList.remove("show", "active");
    tab.classList.remove("active");
    usersTab.classList.add("active");
}

function deleteUser() {
    let userId = document.getElementById("idDelete").value;
    deleteUserFromDB(userId);
}

function fillDeleteModal(userId) {
    let userForForm = getUserFromTable(userId);
    document.getElementById("idDelete").value = userForForm.id;
    document.getElementById("firstNameDelete").value = userForForm.firstName;
    document.getElementById("lastNameDelete").value = userForForm.lastName;
    document.getElementById("ageDelete").value = userForForm.age;
    document.getElementById("emailDelete").value = userForForm.email;
}

function removeUserFromTable(userId) {
    let form = document.getElementById(`user${userId}`);
    form.parentNode.removeChild(form);
}

function fillEditModal(userId) {
    let userForForm = getUserFromTable(userId);
    document.getElementById("idEdit").value = userForForm.id;
    document.getElementById("firstNameEdit").value = userForForm.firstName;
    document.getElementById("lastNameEdit").value = userForForm.lastName;
    document.getElementById("ageEdit").value = userForForm.age;
    document.getElementById("emailEdit").value = userForForm.email;
    document.getElementById("passwordEdit").value = "";
    let roleDoc= document.getElementById("roleEdit").childNodes;
    roleDoc.item(4).selected = false;
    roleDoc.item(2).selected = false;
    let formGet2 = document.getElementById(`user${userId}`);
    let doc2 = formGet2.childNodes;
    if ((doc2.item(11).textContent).includes("ADMIN")) {
        roleDoc.item(4).selected = true;
    }
    if ((doc2.item(11).textContent).includes("USER")) {
        roleDoc.item(2).selected = true;
    }
}

function editUser() {
    let userId = document.getElementById("idEdit").value;
    let user = {
        id: document.getElementById("idEdit").value,
        firstName: document.getElementById("firstNameEdit").value,
        lastName: document.getElementById("lastNameEdit").value,
        age: document.getElementById("ageEdit").value,
        email: document.getElementById("emailEdit").value,
        password: document.getElementById("passwordEdit").value
    }
    let userRole = [];
    let i=0;
    let docRole = document.getElementById("roleEdit").childNodes;
    if (docRole.item(4).selected) {
        userRole[i]="ROLE_ADMIN";
        i++;
    }
    if (docRole.item(2).selected) {
        userRole[i]="ROLE_USER";
    }
    user["role"] = userRole;
    sendReceiveEditedUser(user);
}

function sendReceiveEditedUser(user) {
    fetch("/rest/",{
        method: "PUT",
        headers: {
            'Content-Type': 'application/json;charset= utf-8'
        },
        body:JSON.stringify(user)
    }).then(response=>response.json()).then(res => changeEditedUserField(res)).
    then(console.log("sendReceiveEditedUser"));
}

function changeEditedUserField(changedUser) {
    changeUserLine(changedUser.id);
    let formChange = document.getElementById(`user${changedUser.id}`);
    let doc2 = formChange.childNodes;
    doc2.item(1).textContent = changedUser.id;
    doc2.item(3).textContent = changedUser.firstName;
    doc2.item(5).textContent = changedUser.lastName;
    doc2.item(7).textContent = changedUser.age;
    doc2.item(9).textContent = changedUser.email;
    let roleUser = changedUser.role;
    let role1 = doc2.item(11).childNodes.item(2);
    let role2 = doc2.item(11).childNodes.item(3);
    role1.textContent = "";
    role2.textContent = "";
    if (roleUser[1].name.includes("ADMIN")) {
        role2.textContent = "ADMIN";
    } else {
        role2.textContent = "USER";
    }
    if (roleUser[0] != null) {
        if (roleUser[0].name.includes("ADMIN")) {
            role1.textContent = "ADMIN";
        } else {
            role1.textContent = "USER";
        }
    }
    let formBtn = document.getElementById(`user${changedUser.id}`).childNodes;
    formBtn.item(13).childNodes.item(1).id=`${changedUser.id}`;
    formBtn.item(15).childNodes.item(1).id=`${changedUser.id}`
}

function changeUserLine(userId) {
    let formLine = document.getElementById(`user${userId}`);
    let hiddenUser = document.getElementById("hiddenLine");
    let cloneLine = hiddenUser.cloneNode(true);
    formLine.before(cloneLine);
    formLine.remove();
    let lineUser = document.getElementById("hiddenLine");
    lineUser.hidden = false;
    lineUser.id = `user${userId}`;
}

function getUserFromTable(userId) {
    let doc2 = document.getElementById(`user${userId}`).childNodes;
    let docRole ="";
    let user = {
        id: doc2.item(1).textContent,
        firstName: doc2.item(3).textContent,
        lastName: doc2.item(5).textContent,
        age: doc2.item(7).textContent,
        email: doc2.item(9).textContent
    }
    if ((doc2.item(11).textContent).includes("ADMIN")) {
        docRole = "ADMIN ";
    }
    if ((doc2.item(11).textContent).includes("USER")) {
        docRole = docRole.concat("USER");
    }
    return user;
}

function deleteUserFromDB(userId) {
    fetch(`/rest/${userId}`,{
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json;charset= utf-8'
        },
    }).then(res => resStat(res)).then();
    function resStat(res) {
        if (res.status === 200) removeUserFromTable(userId);
    }
}