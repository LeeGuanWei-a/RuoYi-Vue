import request from '@/utils/request'

// 查询MyWork列表
export function listMyWork(query) {
  return request({
    url: '/schoolJob/MyWork/list',
    method: 'get',
    params: query
  })
}

// 查询MyWork详细
export function getMyWork(workId) {
  return request({
    url: '/schoolJob/MyWork/' + workId,
    method: 'get'
  })
}

// 新增MyWork
export function addMyWork(data) {
  return request({
    url: '/schoolJob/MyWork',
    method: 'post',
    data: data
  })
}

// 修改MyWork
export function updateMyWork(data) {
  return request({
    url: '/schoolJob/MyWork',
    method: 'put',
    data: data
  })
}

// 删除MyWork
export function delMyWork(workId) {
  return request({
    url: '/schoolJob/MyWork/' + workId,
    method: 'delete'
  })
}
