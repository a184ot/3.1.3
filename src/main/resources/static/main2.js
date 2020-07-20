$(document).ready (function () {
    //Create new user
    $('button[name = "create"]').click(function (e) {
        e.preventDefault();
        e.stopPropagation();
        createUser();

        function createUser() {
            var roleName = [];
            $('#roleAdd option:selected').each(function(i, selected){
                roleName[i] ={
                    id: $(selected).val(),
                    name: $(selected).text(),
                    userSet: null}
            });
            let sendUser = {
                firstName: $('#createUserForm').find('#firstName').val(),
                lastName: $('#createUserForm').find('#lastName').val(),
                age: $('#createUserForm').find('#age').val(),
                email: $('#createUserForm').find('#email').val(),
                password: $('#createUserForm').find('#password').val(),
                role: roleName
            }

            $.ajax({
                url:'/rest',
                type:'POST',
                data:JSON.stringify(sendUser),
                dataType:'json',
                contentType:"application/json; charset=utf-8",
                success: function (data) {
                    $("#createUserForm").trigger('reset');
                    addUserClose();
                        let user = data;
                        $('#createUserForm').find('#firstName').val("");
                        $('#createUserForm').find('#lastName').val("");
                        $('#createUserForm').find('#age').val("");
                        $('#createUserForm').find('#email').val("");
                        $('#createUserForm').find('#password').val("");
                    $('#roleAdd option').prop('selected', false);
                    addUserLine(user);
                    changeUserField(user);
                }
            })

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
    })

    // fill delete modal
    $('button[name = "fillDelete"]').click(function (e) {
        let userId = $(this).attr('id');
        fillDeleteModal(userId);
    })

    function fillDeleteModal(userId) {
        let userForForm = getUserFromTable(userId);
        $(`#idDelete`).val(userForForm.id);
        $(`#firstNameDelete`).val(userForForm.firstName);
        $(`#lastNameDelete`).val(userForForm.lastName);
        $(`#ageDelete`).val(userForForm.age);
        $(`#emailDelete`).val(userForForm.email);
    }

    function getUserFromTable(userId) {
        var roleName = [];
        let aa = $(`#user${userId}`).find('.text-nowrap').children();
        for (let i=0; i<aa.length; i++) {
            let roleNameReaded = "ROLE_".concat(aa[i].innerText);
            let roleId = aa[i].firstElementChild.getAttribute("value");
            roleName[i] ={
                id: roleId,
                name: roleNameReaded,
                userSet: null};
        }
        let user = {
            id: $(`#user${userId}`).find('#id').text(),
            firstName: $(`#user${userId}`).find('#firstName').text(),
            lastName: $(`#user${userId}`).find('#lastName').text(),
            age: $(`#user${userId}`).find('#age').text(),
            email: $(`#user${userId}`).find('#email').text(),
            password: $(`#user${userId}`).find('#password').text(),
            role: roleName
        }
        return user;
    }

    // delete user vvv
    $('button[id = "btnDeliteId"]').click(function (e) {
        let userId = $("#idDelete").val();
    deleteUserFromDB(userId);
    })

    function deleteUserFromDB(userId) {
        $.ajax({
            type: "DELETE",
            url: '/rest/' + userId,
            success: function () {
                removeUserFromTable(userId)
            }
        })
    }

    // fill edit modal
    $('button[name = "fillEdit"]').click(function (e) {
        let userId = $(this).attr('id');
        fillEditModal(userId);
    })

    function fillEditModal(userId) {
        let userForForm = getUserFromTable(userId);
        $(`#idEdit`).val(userForForm.id);
        $(`#firstNameEdit`).val(userForForm.firstName);
        $(`#lastNameEdit`).val(userForForm.lastName);
        $(`#ageEdit`).val(userForForm.age);
        $(`#emailEdit`).val(userForForm.email);

        userForFormRole = userForForm.role;
        for (let i=0; i<userForFormRole.length; i++) {
            if (userForFormRole[i].name.includes("ROLE_")) {
                let z=i+1;
                $('#roleEdit option[value=z]').prop('selected', true);
            }
        }
    }

    // edit user vvv
    $('button[id = "btnEditId"]').click(function (e) {
        let userId = $("#idEdit").val();
        editUser();
    })

    function editUser() {
        let userId = $(`#idEdit`).val();
            var roleName = [];
            $('#roleEdit option:selected').each(function(i, selected){
                roleName[i] ={
                    id: $(selected).val(),
                    name: $(selected).text(),
                    userSet: null}
            });
        let user = {
            id: $(`#idEdit`).val(),
            firstName: $(`#firstNameEdit`).val(),
            lastName: $(`#lastNameEdit`).val(),
            age: $(`#ageEdit`).val(),
            email: $(`#emailEdit`).val(),
            password: $(`#passwordEdit`).val(),
            role: roleName
        }
        sendReceiveEditedUser(user);
    }

    function sendReceiveEditedUser(user) {
        $.ajax({
            url: '/rest',
            type: 'PUT',
            data: JSON.stringify(user),
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                removeUserFromTable(data.id);
                addUserLine(data);
                changeUserField(data);
            }
        })
    }
    function changeUserField(changedUser) {
        console.log("change user field 2");
        $(`#user${changedUser.id}`).find('#id').text(changedUser.id);
        $(`#user${changedUser.id}`).find('#firstName').text(changedUser.firstName);
        $(`#user${changedUser.id}`).find('#lastName').text(changedUser.lastName);
        $(`#user${changedUser.id}`).find('#age').text(changedUser.age);
        $(`#user${changedUser.id}`).find('#email').text(changedUser.email);
        let changedUserRole = changedUser.role;
        $.each(changedUserRole, function (i,elem) {
            if (elem != null) {
                let roleId=elem.id;
                let roleName = (elem.name).replace("ROLE_", "");
                $(`#user${changedUser.id}`).find(`[value="${roleId}"]`).text(roleName);
            }
        })
        $(`#user${changedUser.id}`).find(`[data-target="#editModal"]`).attr("id",`${changedUser.id}`);
        $('button[name = "fillEdit"]').on('click',function (e) {
            let userId = $(this).attr('id');
            fillEditModal(userId);
        })

        $(`#user${changedUser.id}`).find(`[data-target="#deleteModal"]`).attr("id",`${changedUser.id}`);
        $('button[name = "fillDelete"]').on('click',function (e) {
            let userId = $(this).attr('id');
            fillDeleteModal(userId);
        })
    }
    function removeUserFromTable(userId) {
        let form = document.getElementById(`user${userId}`);
        form.parentNode.removeChild(form);
    }
    function addUserLine(user) {
        let hiddenUser = document.getElementById("hiddenLine");
        let cloneLine = hiddenUser.cloneNode(true);
        hiddenUser.before(cloneLine);
        let lineUser = document.getElementById("hiddenLine");
        lineUser.hidden = false;
        lineUser.id = `user${user.id}`;
    }

})