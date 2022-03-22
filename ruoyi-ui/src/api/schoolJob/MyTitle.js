import request from '@/utils/request'

// 查询MyTitle列表
export function listMyTitle(query) {
  return request({
    url: '/schoolJob/MyTitle/list',
    method: 'get',
    params: query
  })
}

// 查询MyTitle详细
export function getMyTitle(titleId) {
  return request({
    url: '/schoolJob/MyTitle/' + titleId,
    method: 'get'
  })
}

// 新增MyTitle
export function addMyTitle(data) {
  return request({
    url: '/schoolJob/MyTitle',
    method: 'post',
    data: data
  })
}

// 修改MyTitle
export function updateMyTitle(data) {
  return request({
    url: '/schoolJob/MyTitle',
    method: 'put',
    data: data
  })
}

// 删除MyTitle
export function delMyTitle(titleId) {
  return request({
    url: '/schoolJob/MyTitle/' + titleId,
    method: 'delete'
  })
}
