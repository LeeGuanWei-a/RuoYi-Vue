<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="教学班" prop="className">
        <el-input
          v-model="queryParams.className"
          placeholder="请输入教学班名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教师名称" prop="username">
        <el-input
          v-model="queryParams.username"
          placeholder="请输入教师名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['schoolJob:MyClass:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['schoolJob:MyClass:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['schoolJob:MyClass:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['schoolJob:MyClass:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="MyClassList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="教学班序号" v-if="false" align="center" prop="classId"/>
      <el-table-column label="教学班编号" align="center" prop="classCode"/>
      <el-table-column label="教学班名称" align="center" prop="className"/>
<!--        <template slot-scope="scope">-->
<!--&lt;!&ndash;          <dict-tag :options="dict.type.sys_class" :value="scope.row.className"/>&ndash;&gt;-->
<!--          <dict-tag :value="scope.row.className"/>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="教师名称" align="center" prop="nickName"/>
<!--        <template slot-scope="scope">-->
<!--&lt;!&ndash;          <dict-tag :options="dict.type.sys_class" :value="scope.row.userId"/>&ndash;&gt;-->
<!--          <dict-tag :value="scope.row.userId"/>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['schoolJob:MyClass:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['schoolJob:MyClass:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />


    <!-- 添加或修改MyClass对话框 -->
    <el-dialog title="教学班信息" :visible.sync="open" width="550px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="教学班编号" prop="classCode">
          <el-input v-model="form.classCode" placeholder="请输入教学班编号" />
        </el-form-item>
        <el-form-item label="教学班" prop="className">
          <el-input v-model="form.className" placeholder="请输入教学班名称" />
        </el-form-item>
<!--        <el-form-item label="教师名称" prop="userId">-->
<!--          <el-input v-model="form.userId" placeholder="请输入教师名称" />-->
<!--        </el-form-item>-->
        <el-form-item label="教师名称" prop="userId">
        <el-select v-model="form.userId" label="教师名称" filterable clearable placeholder="请输入教师名称">
            <el-option
              v-for="item in options"
              :key="item.user_id"
              :label="item.nick_name"
              :value="item.user_id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="transfer = true;" style="float: left; background-color: #87CEFA;">关联</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>

    </el-dialog>

    <!--教学班学生管理-->
    <el-form>
      <el-dialog
        title="教学班管理"
        :close-on-click-modal="false"
        :visible.sync="transfer"
        custom-class="width"
      >
        <el-transfer
          filterable
          filter-placeholder="请输入学生名称"
          v-model="form.users"
          :data="users">
        </el-transfer>
      </el-dialog>
    </el-form>


  </div>

</template>

<script>
import { listMyClass, listMyTeachers, getMyClass, delMyClass, addMyClass, updateMyClass } from "@/api/schoolJob/MyClass";

export default {
  name: "MyClass",
  data() {
    return {
      //穿梭框
      transfer: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedSysUser: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // MyClass表格数据
      MyClassList: [],
      // 用户信息表格数据
      sysUserList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        className: null,
        userId: null
      },
      // 表单参数
      form: {},
      //教师列表
      options:[],
      //学生列表
      users:[],
      // 表单校验
      rules: {
      },
      userId: '',
    };
  },
  created() {
    this.getList();
    this.getTeachers();
    this.userId = this.$store.state.user.userId;
  },
  methods: {
    /** 查询MyClass列表 */
    getList() {
      this.loading = true;
      listMyClass(this.queryParams).then(response => {
        this.MyClassList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 查询教师列表
    getTeachers(){
      var that = this;
      listMyTeachers().then(res =>{
        that.options = res
      })
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        classId: null,
        className: null,
        userId: null
      };
      this.sysUserList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.classId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加教学班";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const classId = row.classId || this.ids
      getMyClass(classId).then(response => {
        console.log()
        this.form = response.data;
        this.sysUserList = response.data.sysUserList;
        this.open = true;
        this.title = "修改教学班";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.sysUserList = this.sysUserList;
          if (this.form.classId != null) {
            updateMyClass(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMyClass(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const classIds = row.classId || this.ids;
      this.$modal.confirm('是否确认删除选择的教学班').then(function() {
        return delMyClass(classIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 用户信息序号 */
    rowSysUserIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 用户信息添加按钮操作 */
    handleAddSysUser() {
      let obj = {};
      obj.deptId = "";
      obj.userName = "";
      obj.nickName = "";
      obj.userType = "";
      obj.email = "";
      obj.phonenumber = "";
      obj.sex = "";
      obj.avatar = "";
      obj.password = "";
      obj.status = "";
      obj.loginIp = "";
      obj.loginDate = "";
      obj.remark = "";
      this.sysUserList.push(obj);
    },
    /** 用户信息删除按钮操作 */
    handleDeleteSysUser() {
      if (this.checkedSysUser.length == 0) {
        this.$modal.msgError("请先选择要删除的用户信息数据");
      } else {
        const sysUserList = this.sysUserList;
        const checkedSysUser = this.checkedSysUser;
        this.sysUserList = sysUserList.filter(function(item) {
          return checkedSysUser.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleSysUserSelectionChange(selection) {
      this.checkedSysUser = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('schoolJob/MyClass/export', {
        ...this.queryParams
      }, `MyClass_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style scoped>
  .width{
    min-width: 850px;
    max-width: 850px;
  }
</style>
