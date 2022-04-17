import request from '@/utils/request'

// 查询文件信息详细
export function getInfo(fileId) {
  return request({
    url: '/system/info/' + fileId,
    method: 'get'
  })
}

// 新增文件信息
export function addInfo(data) {
  return request({
    url: '/system/info',
    method: 'post',
    data: data
  })
}


// 删除文件信息
export function delInfo(fileId) {
  return request({
    url: '/system/info/' + fileId,
    method: 'delete'
  })
}
