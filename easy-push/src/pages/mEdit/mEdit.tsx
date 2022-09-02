import MonacoEditor from "react-monaco-editor";
import useStores from "../../utils/store";

const MEditor = () => {
  const { MEditStore } = useStores();

  const { value, editorWillMount, editorDidMount, onChange, editorWillUnmount, language, width, height, theme, options } = MEditStore;
  

  return (
    <MonacoEditor
      width={width}
      height={height}
      language={language}
      theme={theme}
      value={value}
      options={options}
      editorWillMount={editorWillMount}
      editorDidMount={editorDidMount}
      onChange={onChange}
      editorWillUnmount={editorWillUnmount}
    />
  );
};

export default MEditor;
