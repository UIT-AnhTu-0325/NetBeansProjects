import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import axios from "axios";
import { TextField, Button } from "@mui/material";

const baseURLGetALL = "http://localhost:8901/students";
const baseURLAdd = "http://localhost:8901/students/store";
const baseURLEdit = (masv) => {
  return `http://localhost:8901/students/${masv}/update`;
};
const baseURLDelete = (masv) => {
  return `http://localhost:8901/students/${masv}/delete`;
};

export const ListStudent = () => {
  const [students, setStudents] = useState([]);

  const [name, setName] = useState("");
  const [home, setHome] = useState("");
  const [DOB, setDOB] = useState("2023-05-10");

  const [selectedId, setSelectedId] = useState();
  const [formType, setFormType] = useState("Add");

  const columns = [
    { field: "MaSV", headerName: "Mã sinh viên", width: 130 },
    { field: "TenSV", headerName: "Tên sinh viên", width: 250 },
    { field: "QueQuan", headerName: "Quê quán", width: 170 },
    { field: "NgaySinh", headerName: "Ngày sinh", width: 150 },
  ];

  useEffect(() => {
    axios.get(baseURLGetALL).then((response) => {
      const datas = response.data.students;
      const res = datas.map((x) => {
        return { ...x, id: datas.indexOf(x) };
      });
      setStudents(res);
    });
  }, []);

  const handleSubmit = (event) => {
    event.preventDefault();
    const newStudent = {
      TenSV: name,
      QueQuan: home,
      NgaySinh: DOB,
    };
    console.log(newStudent)
    if (formType === "Add") {
      axios.post(baseURLAdd, newStudent).then(() => {
        let maxId = 0;
        students.forEach((item) => {
          maxId = Math.max(item.MaSV, maxId);
        });
        console.log(maxId);
        maxId = maxId + 1;
        let newDatas = [
          ...students,
          {
            MaSV: maxId,
            TenSV: newStudent.TenSV,
            QueQuan: newStudent.QueQuan,
            NgaySinh: newStudent.NgaySinh,
            id: maxId,
          },
        ];
        setStudents(newDatas);
      });
    } else {
      const editedStudent = students.find((x) => x.id === selectedId);
      axios.put(baseURLEdit(editedStudent.MaSV), newStudent).then(() => {
        let newDatas = [...students];
        newDatas = newDatas.map((x) => {
          if (x.id === editedStudent.id) {
            let newData = x;
            newData.TenSV = newStudent.TenSV;
            newData.QueQuan = newStudent.QueQuan;
            newData.NgaySinh = newStudent.NgaySinh;
            return newData;
          }
          return x;
        });
        setStudents(newDatas);
      });
    }
  };

  const handleAddSubject = () => {
    setName("");
    setHome("");
    setDOB("2023-05-10");
    setFormType("Add");
  };

  const handleEditSubject = () => {
    const editedSubject = students.find((x) => x.id === selectedId);
    setName(editedSubject.TenSV);
    setHome(editedSubject.QueQuan);
    let dob = new Date(editedSubject.NgaySinh)
    const offset = dob.getTimezoneOffset()
    dob = new Date(dob.getTime() - (offset*60*1000))
    setDOB(dob.toISOString().split('T')[0]);
    setFormType("Edit");
  };

  const handleRowClick = (e) => {
    setSelectedId(e.row.id);
  };

  const handleDeleteSubject = () => {
    const deleteStudent = students.find((x) => x.id === selectedId);
    if (
      window.confirm(`Are you sure to delete ${deleteStudent.TenSV}`) === true
    ) {
      axios.delete(baseURLDelete(deleteStudent.MaSV)).then(() => {
        let newDatas = [...students];
        newDatas = newDatas.filter((x) => x.MaSV !== deleteStudent.MaSV);
        setStudents(newDatas);
      });
    } else {
      //Anything
    }
  };

  if (!students || students.length === 0) return <div>Loading...</div>;
  return (
    <div>
      <div style={{ margin: 30 }}>
        <h2>List students</h2>
      </div>
      <div style={{ display: "flex" }}>
        <div style={{ width: 900, margin: 30 }}>
          <DataGrid
            rows={students}
            columns={columns}
            initialState={{
              pagination: {
                paginationModel: { page: 0, pageSize: 7 },
              },
            }}
            onRowClick={handleRowClick}
          />
        </div>

        <div style={{ width: 700, margin: 30 }}>
          <div style={{ marginBottom: 30 }}>
            <Button
              variant="outlined"
              color="secondary"
              type="submit"
              style={{ marginRight: 20 }}
              onClick={handleAddSubject}
            >
              Thêm
            </Button>
            <Button
              variant="outlined"
              color="secondary"
              type="submit"
              style={{ marginRight: 20 }}
              onClick={handleEditSubject}
            >
              Sứa
            </Button>
            <Button
              variant="outlined"
              color="secondary"
              type="submit"
              onClick={handleDeleteSubject}
            >
              Xóa
            </Button>
          </div>

          <form autoComplete="off" onSubmit={handleSubmit}>
            <TextField
              label="Tên sinh viên"
              onChange={(e) => setName(e.target.value)}
              required
              variant="outlined"
              color="secondary"
              value={name}
              fullWidth
              sx={{ mb: 3 }}
            />

            <TextField
              label="Quê quán"
              onChange={(e) => setHome(e.target.value)}
              required
              variant="outlined"
              color="secondary"
              value={home}
              fullWidth
              sx={{ mb: 3 }}
            />

            <TextField
                type="date"
                variant='outlined'
                color='secondary'
                label="Date of Birth"
                onChange={e => setDOB(e.target.value)}
                value={DOB}
                fullWidth
                required
                sx={{mb: 4}}
            />

            <Button variant="outlined" color="secondary" type="submit">
              Lưu
            </Button>
          </form>
        </div>
      </div>
    </div>
  );
};
