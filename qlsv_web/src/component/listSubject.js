import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { Navigate, Route, Routes, useNavigate } from "react-router-dom";
import axios from "axios";
import { TextField, Button } from "@mui/material";

const baseURLGetALL = "http://localhost:8901/subjects";
const baseURLAdd = "http://localhost:8901/subjects/store";
const baseURLEdit = (mamh) => {
  return `http://localhost:8901/subjects/${mamh}/update`;
};
const baseURLDelete = (mamh) => {
  return `http://localhost:8901/subjects/${mamh}/delete`;
};

export const ListSubject = () => {
  const [subjects, setSubjects] = useState([]);

  const [name, setName] = useState("");
  const [nums, setNums] = useState(0);

  const [selectedId, setSelectedId] = useState();
  const [formType, setFormType] = useState("Add");

  const navigate = useNavigate();

  const columns = [
    { field: "MaMH", headerName: "Mã môn học", width: 130 },
    { field: "TenMH", headerName: "Tên môn học", width: 300 },
    { field: "STC", headerName: "Số tín chỉ", width: 150 },
  ];

  useEffect(() => {
    axios.get(baseURLGetALL).then((response) => {
      const datas = response.data.subjects;
      const res = datas.map((x) => {
        return { ...x, id: datas.indexOf(x) };
      });
      setSubjects(res);
    });
  }, []);

  const handleSubmit = (event) => {
    event.preventDefault();
    const newSubject = {
      TenMH: name,
      STC: nums,
    };
    if (formType === "Add") {
      axios.post(baseURLAdd, newSubject).then(() => {
        let maxId = 0;
        subjects.forEach((item) => {
          maxId = Math.max(item.MaMH, maxId);
        });
        console.log(maxId);
        maxId = maxId + 1;
        let newDatas = [
          ...subjects,
          {
            MaMH: maxId,
            TenMH: newSubject.TenMH,
            STC: newSubject.STC,
            id: maxId,
          },
        ];
        setSubjects(newDatas);
      });
    } else {
      const editedSubject = subjects.find((x) => x.id === selectedId);
      axios.put(baseURLEdit(editedSubject.MaMH), newSubject).then(() => {
        let newDatas = [...subjects];
        newDatas = newDatas.map((x) => {
          if (x.id === editedSubject.id) {
            let newData = x;
            newData.TenMH = newSubject.TenMH;
            newData.STC = newSubject.STC;
            return newData;
          }
          return x;
        });
        setSubjects(newDatas);
      });
    }
  };

  const handleAddSubject = () => {
    setName("");
    setNums(0);
    setFormType("Add");
  };

  const handleEditSubject = () => {
    const editedSubject = subjects.find((x) => x.id === selectedId);
    setName(editedSubject.TenMH);
    setNums(editedSubject.STC);
    setFormType("Edit");
  };

  const handleRowClick = (e) => {
    setSelectedId(e.row.id);
  };

  const handleDeleteSubject = () => {
    const deleteSubject = subjects.find((x) => x.id === selectedId);
    if (window.confirm(`Are you sure to delete ${deleteSubject.TenMH}`) === true) {
      axios.delete(baseURLDelete(deleteSubject.MaMH)).then(() => {
        let newDatas = [...subjects];
        newDatas = newDatas.filter(x => x.MaMH !== deleteSubject.MaMH)
        setSubjects(newDatas);
      });
    } else {
      //Anything
    }
  };

  const handleStudentGrade = () => {
    const subject = subjects.find((x) => x.id === selectedId);
    navigate(`/subjects/${subject.MaMH}`);
  }

  if (!subjects || subjects.length === 0) return <div>Loading...</div>;
  return (
    <div>
      <div style={{ margin: 30 }}>
        <h2>List subjects</h2>
      </div>
      <div style={{ display: "flex" }}>
        <div style={{ width: 900, margin: 30 }}>
          <DataGrid
            rows={subjects}
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
              style={{ marginRight: 30 }}
              onClick={handleDeleteSubject}
            >
              Xóa
            </Button>
            <Button
              variant="outlined"
              color="secondary"
              type="submit"
              onClick={handleStudentGrade}
            >
              Xem điểm
            </Button>
          </div>

          <form autoComplete="off" onSubmit={handleSubmit}>
            <TextField
              label="Tên môn học"
              onChange={(e) => setName(e.target.value)}
              required
              variant="outlined"
              color="secondary"
              value={name}
              fullWidth
              sx={{ mb: 3 }}
            />

            <TextField
              label="Số tín chỉ"
              onChange={(e) => setNums(e.target.value)}
              required
              variant="outlined"
              color="secondary"
              value={nums}
              fullWidth
              sx={{ mb: 3 }}
              type="number"
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
