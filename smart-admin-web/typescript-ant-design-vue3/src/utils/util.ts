export const timestampToStr = (timestamp) => {
    // 使用new Date()创建一个Date对象，并将时间戳传递给构造函数
    var date = new Date(timestamp);
    // 使用Date对象的方法获取年、月、日、时、分、秒
    var year = date.getFullYear();
    var month = ('0' + (date.getMonth() + 1)).slice(-2);
    var day = ('0' + date.getDate()).slice(-2);
    var hours = ('0' + date.getHours()).slice(-2);
    var minutes = ('0' + date.getMinutes()).slice(-2);
    var seconds = ('0' + date.getSeconds()).slice(-2);
    // 构建最终的日期时间字符串
    var formattedDateTime = year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;
    return formattedDateTime;
} 