/**
 * 深拷贝
 * @param obj 需要拷贝的对象
 */
const deepCopy = obj => {
    if (obj === null || typeof obj !== 'object') {
        return obj;
    }
    let copy = Array.isArray(obj) ? [] : {};
    for (let key in obj) {
        if (Object.prototype.hasOwnProperty.call(obj, key)) {
            copy[key] = deepCopy(obj[key]);
        }
    }
    return copy;
}

/**
 * 判断对象是否为空对象
 *
 * @param obj 需要判断的对象
 */
const isEmptyObject = obj => {
    // 获取对象的键数组
    const keys = Object.keys(obj);
    // 如果键数组的长度为0，则对象为空对象
    return keys.length === 0;
}

export default {deepCopy, isEmptyObject}