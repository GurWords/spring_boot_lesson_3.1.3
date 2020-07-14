$(document).ready(function() {
    $.ajax({
        url: 'admin/list',
        type: "get",
        cache: false,
        success: function(response){
            $.each(response,function (key,value) {
                $('#tbodyId').after(`
                    <tr id='tr-${value.id}'>
                    <td><input class='form-control-plaintext' value="${value.id}" readonly />
                    </td>
                    <td><input value="${value.name}" class='form-control-plaintext' readonly />
                    </td>
                    <td><input value="${value.age}" class='form-control-plaintext' readonly />
                    </td>
                    <td><input value="${value.password}" class='form-control-plaintext' readonly />
                    </td>
                    <td><input id="role-${value.id}" class='form-control-plaintext' readonly />
                    </td>  
                     <td >
                    <button type='button' class='btn btn-primary' data-toggle='modal' data-target='#modalUpdate' data-user-id='${value.id}'>
                    Edit
                    </button>
                    </td>
                    <td>
                    <button type='button' class='btn btn-primary' data-toggle='modal' data-target='#modalDelete' data-user-id='${value.id}'>
                    Delete
                    </button>
                    </td>
                    </tr>
                    `);
                $.each(value.roleSet, function (key, value1) {
                    $(`#role-${value.id}`).val(`${$(`#role-${value.id}`).val()}${value1.role},`);
                });
            });
        }
    });

    $.ajax({
        url: 'admin/userCurrent',
        type : 'get',
        cache: "false",
        success:function (response) {
            let t = [];
            $.each(response.roleSet, function (key,value) {
                t[key] = value.role;
            })
            $('#AdminSpanHead').text(`${response.name} with Roles: ${t}`);
            $('#userCurrentTable').html(`
                    <tr>
                    <td><input class='form-control-plaintext' value="${response.id}" readonly />
                    </td>
                    <td><input class='form-control-plaintext' value="${response.name}" readonly />
                    </td>
                    <td><input  class='form-control-plaintext' value="${response.age}" readonly />
                    </td>
                    <td><input  class='form-control-plaintext' value="${response.password}" readonly />
                    </td>
                    <td><input  class='form-control-plaintext' value="${t}" readonly />
                    </td>
                    </tr>`
            );
        }
    })
    $('#formUpdateUser').on("submit",function (event) {
        event.preventDefault();
        $.ajax({
            url: 'admin/update',
            type : 'put',
            cache : false,
            //data_type: 'application/json;charset=utf-8',
            contentType : 'application/json;charset=utf-8',
            data: $(this).serialize(),
            success: function (response) {
                let i = 0;
                let test1 = [response.id,response.name,response.age,response.password,$('#selectFormUpdate').val()];
                $(".modal").modal("hide");
                alert("Успешно обновлено" + response.id +response.name);
                $(`#tr-${response.id}`).find('input').each(function () {
                    $(this).val(test1[i]);
                    i++;
                });
            }
        })
    })
    $('#formDelete').on("submit",function (event) {
        event.preventDefault();
        $.ajax({
            url:'admin/delete',
            type:'delete',
            cache: false,
            data: $(this).serialize(),
            success: function (response) {
                $(".modal").modal("hide");
                $(`#tr-${response.id}`).remove();
            }
        })
    })
    $('#formInsert').on("submit",function (event) {
        event.preventDefault();
        $.ajax({
            url : 'admin/insert',
            type : 'post',
            cache: false,
            data: $(this).serialize(),
            success : function (response) {
                $('#formInputAjaxName').val('');
                $('#formInputAjaxPassword').val('');
                $('#formInputAjaxAge').val('');
                alert("Добавление прошло успешно");
                $('#tbodyId').after(`
                    <tr id='tr-${response.id}'>
                    <td><input id='ajaxIdInsert' class='form-control-plaintext' value="${response.id}" readonly />
                    </td>
                    <td><input id='ajaxNameInsert' class='form-control-plaintext' readonly />
                    </td>
                    <td><input id='ajaxAgeInsert' class='form-control-plaintext' readonly />
                    </td>
                    <td><input id='ajaxPasswordInsert' class='form-control-plaintext' readonly />
                    </td>
                    <td><input id='ajaxRoleInsert2' class='form-control-plaintext' readonly />
                    </td>
                    <td >
                    <button type='button' class='btn btn-primary' data-toggle='modal' data-target='#modalUpdate' data-user-id='${response.id}'>
                    Edit
                    </button>
                    </td>
                    <td>
                    <button type='button' class='btn btn-primary' data-toggle='modal' data-target='#modalDelete' data-user-id='${response.id}'>
                    Delete
                    </button>
                    </td>
                    </tr>`
                );
                $('#ajaxNameInsert').val(response.name);
                $('#ajaxAgeInsert').val(response.age);
                $('#ajaxPasswordInsert').val(response.password);
                $('#ajaxRoleInsert2').val($('#selectRoleInsert').val());
            }
        })

    })

    $("#modalUpdate").on('show.bs.modal', function (e) {
        let userId = $(e.relatedTarget).data('user-id');
        let test = [];

        $(`#tr-${userId}`).find('input').each(function () {
            test.push($(this).val());
        });


        let name = test[1];
        let age = test[2];
        let password = test[3];
        let role = test[4];

        $('#modalUpdateId').val(test[0])
        $("#modalUpdateName").val(name);
        $('#modalUpdateAge').val(age);
        $('#modalUpdatePassword').val(password);

    });
    $("#modalDelete").on('show.bs.modal', function (e) {
        let userId = $(e.relatedTarget).data('user-id');
        let test = [];

        $(`#tr-${userId}`).find('input').each(function () {
            test.push($(this).val());
        });


        let name = test[1];
        let age = test[3];
        let password = test[2];
        let role = test[4];

        $('#modalDeleteId').val(test[0])
        $("#modalDeleteName").val(name);
        $('#modalDeleteAge').val(age);
        $('#modalDeletePassword').val(password);
        $('#modelDeleteRole').val(role);
    });
});