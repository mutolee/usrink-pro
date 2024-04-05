/**
 * 给字符串添加换行符，每隔 interval 个字符添加一个换行符
 * @param text 字符串
 * @param interval 间隔
 * @return {string}
 */
const addLineBreaks = (text, interval) => {
    let result = '';
    for (let i = 0; i < text.length; i++) {
        result += text[i];
        if ((i + 1) % interval === 0 && i !== text.length - 1) {
            result += '<br/>';
        }
    }
    return result;
}

/**
 * 逗号分割的字符串转数组
 *
 * @param text 字符串
 */
const stringToArray = (text) => {
    if (text === null || text === undefined) {
        return [];
    }
    return text.split(',');
}

/**
 * 数组转逗号分割的字符串
 */
const arrayToString = (array) => {
    if (array === null || array === undefined) {
        return '';
    }
    return array.join(',');
}


export default {addLineBreaks, stringToArray, arrayToString}