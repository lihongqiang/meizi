/**
 * Created by user-u1 on 2016/11/13.
 */
/*Javascript代码片段*/
var t = $(document).ready( function () {
    $('#table_id_example').DataTable();
} );

//前台添加序号
t.on('order.dt search.dt',
    function() {
        t.column(0, {
            "search": 'applied',
            "order": 'applied'
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1;
        });
    }).draw();

//更换数据源（相同格式，但是数据内容不同）
//$("#redraw").click(function() {
//    var url = table.api().ajax.url("http://www.gbtags.com/gb/networks/uploads/a7bdea3c-feaf-4bb5-a3bd-f6184c19ec09/newData.txt");
//    url.load();
//});