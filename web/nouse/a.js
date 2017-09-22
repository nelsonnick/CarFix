this.$http.get(
  API.save,
  { params: {
    number: this.number,
    brand: this.brand,
    remark: this.remark
  } },
  { headers: { 'X-Requested-With': 'XMLHttpRequest' } }
).then((response) => {
  this.Saving = false
  if (response.body === 'OK') {
    this.openPopup('保存成功！', 'check_circle', 'green')
    setTimeout(() => { this.$router.push({ path: '/list' }) }, 1000)
  } else {
    this.openPopup(response.body, 'report_problem', 'orange')
  }
  this.Saving = false
}, (response) => {
  this.Saving = false
  this.openPopup('服务器内部错误!', 'error', 'red')
})
