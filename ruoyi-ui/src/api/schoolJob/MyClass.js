import request from '@/utils/request'

// 查询MyClass列表
export function listMyClass(query) {
  return request({
    url: '/schoolJob/MyClass/list',
    method: 'get',
    params: query
  })
}

// 查询教师列表
export function listMyTeachers() {
  return request({
    url: '/schoolJob/MyClass/teachers',
    method: 'get',
  })
}


// 查询MyClass详细
export function getMyClass(classId) {
  return request({
    url: '/schoolJob/MyClass/' + classId,
    method: 'get'
  })
}

// 新增MyClass
export function addMyClass(data) {
  return request({
    url: '/schoolJob/MyClass',
    method: 'post',
    data: data
  })
}

// 修改MyClass
export function updateMyClass(data) {
  return request({
    url: '/schoolJob/MyClass',
    method: 'put',
    data: data
  })
}

// 删除MyClass
export function delMyClass(classId) {
  return request({
    url: '/schoolJob/MyClass/' + classId,
    method: 'delete'
  })
}
