$(document).ready(function () {
    $.ajax({
        url: 'user',
        type: 'get',
        cache: false,
        success: function (response) {
            let t = [];
            $.each(response.roleSet,function (key,value) {
                    t[key] = value.role;
            })
            $('#tbodyUser').after(`
                    <tr>
                    <td><input class='form-control-plaintext' value="${response.id}" readonly />
                    </td>
                    <td><input class='form-control-plaintext' value="${response.name}" readonly />
                    </td>
                    <td><input class='form-control-plaintext' value="${response.age}" readonly />
                    </td>
                    <td><input class='form-control-plaintext' value="${response.password}" readonly />
                    </td>
                    <td><input class='form-control-plaintext' value="${t}" readonly />
                    </td>
                    </tr>`
            );
        }
    })
})